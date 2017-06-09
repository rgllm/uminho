
#include "tinyxml/tinyxml.h"
#include "tinyxml/tinystr.h"
#include <vector>
#include <iostream>
#include <fstream>
#include <string>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif
#include <math.h>
#define M_PI_2     1.57079632679489661923   // pi/2

using namespace std;
double alpha = 0, beta = 0, r = 10;
double px = 0, py = 0, pz = 0;
int polMode = 0;
class Ponto {
public:
	double x;
	double y;
	double z;
};
vector<vector<Ponto>> modelos;
double angulo1 = 0.0;
double angulo2 = 0.0;


void changeSize(int w, int h) {

	// Prevent a divide by zero, when window is too short
	// (you cant make a window with zero width).
	if (h == 0)
		h = 1;

	// compute window's aspect ratio 
	double ratio = w * 1.0 / h;

	// Set the projection matrix as current
	glMatrixMode(GL_PROJECTION);
	// Load Identity Matrix
	glLoadIdentity();

	// Set the viewport to be the entire window
	glViewport(0, 0, w, h);

	// Set perspective
	gluPerspective(45.0f, ratio, 1.0f, 1000.0f);

	// return to the model view matrix mode
	glMatrixMode(GL_MODELVIEW);
}


void renderScene(void) {
	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set the camera
	glLoadIdentity();
	gluLookAt(px, py, pz,
		0.0, 0.0, 0.0,
		0.0f, 1.0f, 0.0f);

	glRotatef(angulo1, 0, 1, 0);
	glRotatef(angulo2, 1, 0, 0);
	// put the geometric transformations here

	// put drawing instructions here
	for (unsigned i = 0; i < modelos.size(); i++)
		for (unsigned j = 0; j < modelos[i].size(); j += 3) {
			glBegin(GL_TRIANGLES);
			glVertex3f(modelos[i][j].x, modelos[i][j].y, modelos[i][j].z);
			glVertex3f(modelos[i][j + 1].x, modelos[i][j + 1].y, modelos[i][j + 1].z);
			glVertex3f(modelos[i][j + 2].x, modelos[i][j + 2].y, modelos[i][j + 2].z);
			glEnd();

		}

	// End of frame
	glutSwapBuffers();
}

// write function to process keyboard events

void modo(int button, int state, int x, int y) {
	polMode = (polMode + 1) % 3;
	if (button == GLUT_LEFT_BUTTON && state == GLUT_DOWN) {
		if (polMode == 0)
			glPolygonMode(GL_FRONT, GL_FILL);
		if (polMode == 1)
			glPolygonMode(GL_FRONT, GL_LINE);
		if (polMode == 2)
			glPolygonMode(GL_FRONT, GL_POINT);

	}
	
}

void refreshCamPosition() {
	px = r*cos(beta)*sin(alpha);
	py = r*sin(beta);
	pz = r*cos(beta)*cos(alpha);
}

void processKeys(unsigned char c, int xx, int yy) {

	// put code to process regular keys in here
	switch (c) {
	case 'w':
		if (r >= 0.25)
			r -= 0.25;
		break;
	case 's':
		r += 0.25;
		break;
	}
	refreshCamPosition();
	glutPostRedisplay();
}


void processSpecialKeys(int key, int xx, int yy) {

	// put code to process special keys in here
	switch (key) {
	case GLUT_KEY_UP:
		if (beta <= M_PI_2 - 0.1)
			beta += 0.1;
		break;
	case GLUT_KEY_DOWN:
		if (beta >= -M_PI_2 + 0.1)
			beta -= 0.1;
		break;
	case GLUT_KEY_LEFT:
		alpha += 0.1;
		break;
	case GLUT_KEY_RIGHT:
		alpha -= 0.1;
		break;

	}

	refreshCamPosition();
	glutPostRedisplay();

}


int main(int argc, char **argv) {
	if (argc < 2) {
		cout << "sintaxe : drawer <fileName>.xml\n";
		return -1;
	}
	string fileDir = "../../../xml_3d/";
	string xmlFile = fileDir + argv[1];
	TiXmlDocument doc(xmlFile.c_str());
	if (!doc.LoadFile()) {
		cout << "erro a ler o ficheiro xml" << endl;
		return -2;
	}
	TiXmlElement* root = doc.RootElement();
	for (TiXmlElement* pElement = root->FirstChild("model")->ToElement(); pElement; pElement = pElement->NextSiblingElement()) {
		const char *ficheiro = pElement->Attribute("file");
		if (ficheiro) {
			fstream f;
			string file3d = fileDir + ficheiro;
			f.open(file3d, ios::in);
			if (f.is_open()) {
				string line;
				vector<Ponto> pontos;
				getline(f, line);
				while (line.compare("FIM")) {
					double x, y, z;
					sscanf(line.c_str(), "%lf %lf %lf\n", &x, &y, &z);
					Ponto p;
					p.x = x; p.y = y; p.z = z;
					pontos.push_back(p);
					getline(f, line);
				}
				modelos.push_back(pontos);
				f.close();
			}
			else {
				cout << "erro ao abrir ficheiro .3d" << endl;
				return -2;
			}
		}
		else {
			cout << "erro no ficheiro xml" << endl;
			return -3;
		}
	}

	// init GLUT and the window
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
	glutInitWindowPosition(100, 100);
	glutInitWindowSize(800, 800);
	glutCreateWindow("CG@DI-UM");
	refreshCamPosition();


	// Required callback registry 
	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);


	// put here the registration of the keyboard callbacks
	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);
	glutIdleFunc(renderScene);
	glutMouseFunc(modo);
	glutKeyboardFunc(processKeys);
	glutSpecialFunc(processSpecialKeys);

	//  OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

	// enter GLUT's main cycle
	glutMainLoop();

	return 1;
}
