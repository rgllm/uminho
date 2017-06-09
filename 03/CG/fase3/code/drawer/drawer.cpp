
#include <stdio.h>
#include <stdlib.h>
#include <string>
#define _USE_MATH_DEFINES
#include <math.h>
#include <vector>
#include <fstream>
#include <iostream>
#include "tinyxml/tinyxml.h"
#include "tinyxml/tinystr.h"

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif

using namespace std;

string fileDir = "../../../xml_3d/";


int startX, startY, tracking = 0;

int alpha = 0, beta = 0, r = 100;
float camX = 0, camY, camZ = r;
int polMode = 0;
int timebase = 0, frame = 0;
void* font = GLUT_BITMAP_9_BY_15;

float up[3] = { 0.0f, -1.0f, 0.0f };

GLuint * buffers;

int nModels = 0 , nCatmmulls=0 ,nRots=0;
int counterT,counterBuff,counterRot;
float *t, *rot;
class Point {
public:
	float x;
	float y;
	float z;
};
class Group {
public:
	vector<Point> points;
	float translateTime;
	vector<Point> controlPoints;
	Point rotation;
	Point scale;
	float rotateTime;
	vector<Group>childs;
	float deltaT;
	bool hasModels;
};
vector<Group> scene;
vector<vector<Point>> models;


void buildRotMatrix(float *x, float *y, float *z, float *m) {

	m[0] = x[0]; m[1] = x[1]; m[2] = x[2]; m[3] = 0;
	m[4] = y[0]; m[5] = y[1]; m[6] = y[2]; m[7] = 0;
	m[8] = z[0]; m[9] = z[1]; m[10] = z[2]; m[11] = 0;
	m[12] = 0; m[13] = 0; m[14] = 0; m[15] = 1;
}


void cross(float *a, float *b, float *res) {

	res[0] = a[1] * b[2] - a[2] * b[1];
	res[1] = a[2] * b[0] - a[0] * b[2];
	res[2] = a[0] * b[1] - a[1] * b[0];
}


void normalize(float *a) {

	float l = sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
	a[0] = a[0] / l;
	a[1] = a[1] / l;
	a[2] = a[2] / l;
}


float length(float *v) {

	float res = sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
	return res;

}

void multMatrixVector(float *m, float *v, float *res) {

	for (int j = 0; j < 4; ++j) {
		res[j] = 0;
		for (int k = 0; k < 4; ++k) {
			res[j] += v[k] * m[j * 4 + k];
		}
	}

}


void getCatmullRomPoint(float t, float *p0, float *p1, float *p2, float *p3, float *res, float *deriv) {
	//cout << t << endl;
	// catmull-rom matrix
	float m[4][4] = { { -0.5f,  1.5f, -1.5f,  0.5f },
	{ 1.0f, -2.5f,  2.0f, -0.5f },
	{ -0.5f,  0.0f,  0.5f,  0.0f },
	{ 0.0f,  1.0f,  0.0f,  0.0f } };

	// reset res and deriv
	res[0] = 0.0; res[1] = 0.0; res[2] = 0.0;
	deriv[0] = 0.0; deriv[1] = 0.0; deriv[2] = 0.0;
	float A[3][4];
	// Compute A = M * P
	for (int i = 0; i < 3; i++)
	{
		float pl[] = { p0[i], p1[i], p2[i], p3[i] };
		multMatrixVector((float*)m, pl, A[i]);
	}
	// Compute point res = T *A
	// compute deriv = T' * A
	float v_t[4] = { t*t*t, t*t, t, 1 };
	float v_td[4] = { 3 * (t*t), 2 * t, 1, 0 };
	for (int j = 0; j < 3; j++)
	{
		for (int i = 0; i < 4; i++)
		{
			res[j] += v_t[i] * A[j][i];
			deriv[j] += v_td[i] * A[j][i];
		}
	}

}


// given  global t, returns the point in the curve
void getGlobalCatmullRomPoint(float gt, float *pos, float *deriv, vector<Point> controlPoints) {
	int pointCount = controlPoints.size();
	float t = gt * pointCount; // this is the real global t
	int index = floor(t);  // which segment
	t = t - index; // where within  the segment

				   // indices store the points
	int indices[4];
	indices[0] = (index + pointCount - 1) % pointCount;
	indices[1] = (indices[0] + 1) % pointCount;
	indices[2] = (indices[1] + 1) % pointCount;
	indices[3] = (indices[2] + 1) % pointCount;
	float **p = new float*[pointCount];

	for (int i = 0; i < pointCount; i++) {
		p[i] = new float[3];
		p[i][0] = controlPoints[i].x;
		p[i][1] = controlPoints[i].y;
		p[i][2] = controlPoints[i].z;
	}

	getCatmullRomPoint(t, p[indices[0]], p[indices[1]], p[indices[2]], p[indices[3]], pos, deriv);

	for (int i = 0; i<pointCount; i++) {
		delete[] p[i];
	}
	delete[] p;
}


void changeSize(int w, int h) {

	// Prevent a divide by zero, when window is too short
	// (you cant make a window with zero width).
	if (h == 0)
		h = 1;

	// compute window's aspect ratio 
	float ratio = w * 1.0 / h;

	// Reset the coordinate system before modifying
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	// Set the viewport to be the entire window
	glViewport(0, 0, w, h);

	// Set the correct perspective
	gluPerspective(45, ratio, 1, 1000);

	// return to the model view matrix mode
	glMatrixMode(GL_MODELVIEW);
}

void drawScene(vector<Group>groups) {
	for (unsigned i = 0; i < groups.size(); i++) {
		Group group = groups[i];
		float time = glutGet(GLUT_ELAPSED_TIME)*0.001;
		glPushMatrix();
		//apply transformations here

		if (group.controlPoints.size() > 3) {
			// desenhar orbita
			glBegin(GL_LINE_LOOP);
			float t2 = 0.0f;
			for (int i = 0; i < 1000; i++) {
				float res2[3];
				float deriv2[3];

				getGlobalCatmullRomPoint(t2, (float*)res2, (float*)deriv2, group.controlPoints);
				glVertex3f(res2[0], res2[1], res2[2]);
				t2 += 0.001f;
			}
			glEnd();
			float res[3];
			float deriv[3];
			float M[4][4];

			M[0][3] = M[1][3] = M[2][3] = 0.0f;
			M[3][3] = 1.0f;
			getGlobalCatmullRomPoint(t[counterT], (float*)res, (float*)deriv, group.controlPoints);
			t[counterT++] = time / group.translateTime;

			normalize(deriv);

			M[0][0] = deriv[0];
			M[0][1] = deriv[1];
			M[0][2] = deriv[2];

			M[3][0] = res[0];
			M[3][1] = res[1];
			M[3][2] = res[2];

			/* Fill Z row*/
			float Z[3];
			cross(deriv, up, Z);
			normalize(Z);

			M[2][0] = Z[0];
			M[2][1] = Z[1];
			M[2][2] = Z[2];

			/* up => cross product: Z x deriv*/
			cross(Z, deriv, up);
			normalize(up);

			M[1][0] = up[0];
			M[1][1] = up[1];
			M[1][2] = up[2];

			glMultMatrixf((float*)M);
		}
		glScalef(group.scale.x, group.scale.y, group.scale.z);
		if (group.rotateTime > 0) {
			glRotatef(rot[counterRot], group.rotation.x, group.rotation.y, group.rotation.z);
			rot[counterRot++] = time / group.rotateTime * 360;
		}

		drawScene(group.childs);

		if (group.hasModels) {
			glBindBuffer(GL_ARRAY_BUFFER, buffers[counterBuff]);
			glVertexPointer(3, GL_FLOAT, 0, 0);
			glDrawArrays(GL_TRIANGLES, 0, models[counterBuff++].size()*3);
		}
		glPopMatrix();
	}
}

void renderScene(void) {

	float fps;
	int time;
	char s[64];
	counterT = counterBuff = counterRot = 0;

	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glLoadIdentity();
	gluLookAt(camX, camY, camZ,
		0.0, 0.0, 0.0,
		0.0f, 1.0f, 0.0f);

	//renderCatmullRomCurves(scene);

	drawScene(scene);

	frame++;
	time = glutGet(GLUT_ELAPSED_TIME);
	if (time - timebase > 1000) {
		fps = frame*1000.0 / (time - timebase);
		timebase = time;
		frame = 0;
		sprintf(s, "FPS: %6.2f", fps);
		glutSetWindowTitle(s);
	}
	glutSwapBuffers();
}
Group calculateGroupPoints(TiXmlElement* group) {
	Group ret;
	if (!group) {
		return ret;
	}
	for (TiXmlElement* childGroup = group->FirstChildElement("group"); childGroup; childGroup = childGroup->NextSiblingElement())
		ret.childs.push_back(calculateGroupPoints(childGroup));

	TiXmlElement* transl = group->FirstChildElement("translate");

	if (transl) {
		nCatmmulls++;
		if (transl->Attribute("time")) {
			ret.translateTime = atof(transl->Attribute("time"));
			if (ret.translateTime > 0) {
				ret.deltaT = 1 / ret.translateTime*0.001;
				for (TiXmlElement* point = transl->FirstChildElement("point"); point; point = point->NextSiblingElement()) {
					Point p;
					p.x = atof(point->Attribute("X"));
					p.y = atof(point->Attribute("Y"));
					p.z = atof(point->Attribute("Z"));
					ret.controlPoints.push_back(p);
				}
			}
		}
	}

	ret.rotation.x = ret.rotation.y = ret.rotation.z = 0;
	ret.rotateTime = 0;
	TiXmlElement* rot = group->FirstChildElement("rotate");
	if (rot) {
		nRots++;
		if (rot->Attribute("time"))
			ret.rotateTime = atof(rot->Attribute("time"));
		if (rot->Attribute("axisX"))
			ret.rotation.x = atof(rot->Attribute("axisX"));
		if (rot->Attribute("axisY"))
			ret.rotation.y = atof(rot->Attribute("axisY"));
		if (rot->Attribute("axisZ"))
			ret.rotation.z = atof(rot->Attribute("axisZ"));
	}
	ret.scale.x = ret.scale.y = ret.scale.z = 1;
	TiXmlElement* sc = group->FirstChildElement("scale");
	if (sc) {
		if (sc->Attribute("X"))
			ret.scale.x = atof(sc->Attribute("X"));
		if (sc->Attribute("Y"))
			ret.scale.y = atof(sc->Attribute("Y"));
		if (sc->Attribute("Z"))
			ret.scale.z = atof(sc->Attribute("Z"));
	}

	TiXmlElement* models2 = group->FirstChildElement("models");
	ret.hasModels = false;
	if (models2) {
		nModels++;
		vector<Point> aux;
		for (TiXmlElement* model = models2->FirstChildElement("model"); model; model = model->NextSiblingElement()) {
			ret.hasModels = true;
			const char *file = model->Attribute("file");
			if (file) {
				fstream f;
				string file3d = fileDir + file;
				f.open(file3d, ios::in);
				if (f.is_open()) {
					string line;
					getline(f, line);
					while (line.compare("FIM")) {
						double x, y, z;
						sscanf(line.c_str(), "%lf %lf %lf\n", &x, &y, &z);
						Point p;
						p.x = x;
						p.y = y;
						p.z = z;
						ret.points.push_back(p);
						aux.push_back(p);
						getline(f, line);
					}
					f.close();
				}
				else {
					cout << "erro ao abrir ficheiro .3d" << endl;
				}
			}
			else {
				cout << "erro no ficheiro xml" << endl;
			}
		}
		models.push_back(aux);
	}
	return ret;

}



void processMouseButtons(int button, int state, int xx, int yy)
{
	if (state == GLUT_DOWN) {
		startX = xx;
		startY = yy;
		if (button == GLUT_LEFT_BUTTON)
			tracking = 1;
		else if (button == GLUT_RIGHT_BUTTON)
			tracking = 2;
		else tracking = 0;
		
	}
	else if (state == GLUT_UP) {
		if (tracking == 1) {
			alpha += (xx - startX);
			beta += (yy - startY);
		}
		else if (tracking == 2) {

			r -= yy - startY;
			if (r < 3)
				r = 3.0;
		}
		
		tracking = 0;
	}

}


void processMouseMotion(int xx, int yy)
{
	int deltaX, deltaY;
	int alphaAux, betaAux;

	if (!tracking)
		return;

	deltaX = xx - startX;
	deltaY = yy - startY;

	if (tracking == 1) {

		alphaAux = alpha + deltaX;
		betaAux = beta + deltaY;

		if (betaAux > 85.0)
			betaAux = 85.0;
		else if (betaAux < -85.0)
			betaAux = -85.0;

	}
	else if (tracking == 2) {

		alphaAux = alpha;
		betaAux = beta;
		r = r - deltaY;
		if (r < 3)
			r = 3;
	}
	camX = r * sin(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
	camZ = r * cos(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
	camY = r *							     sin(betaAux * 3.14 / 180.0);
}

void processKeyboard(unsigned char key, int x, int y)
{
	switch (key)
	{
	case 'm':
		polMode = (polMode + 1) % 3;
		if (polMode == 0)
			glPolygonMode(GL_FRONT, GL_FILL);
		if (polMode == 1)
			glPolygonMode(GL_FRONT, GL_LINE);
		if (polMode == 2)
			glPolygonMode(GL_FRONT, GL_POINT);
		break;
	case 'w':
		if (r - 5 > 0) {
			r -= 5;
			camX = r * sin(alpha * 3.14 / 180.0) * cos(beta * 3.14 / 180.0);
			camZ = r * cos(alpha * 3.14 / 180.0) * cos(beta * 3.14 / 180.0);
			camY = r *							     sin(beta * 3.14 / 180.0);
		}
		break;
	case 's':
		r+=5;
		camX = r * sin(alpha * 3.14 / 180.0) * cos(beta * 3.14 / 180.0);
		camZ = r * cos(alpha * 3.14 / 180.0) * cos(beta * 3.14 / 180.0);
		camY = r *							     sin(beta * 3.14 / 180.0);
		break;
	}
	
}

void fillBuffers(vector<Group> groups) {
	for (int i = 0; i < groups.size(); i++) {
		Group g = groups[i];
		fillBuffers(g.childs);
		if (g.hasModels) {
			vector<Point> points = models[counterT];
			float * vertices;
			vertices = (float*)malloc(sizeof(float)*points.size() * 3);
			for (int j = 0; j < points.size(); j++) {
				vertices[3 * j] = points[j].x;
				vertices[3 * j + 1] = points[j].y;
				vertices[3 * j + 2] = points[j].z;
			}
			glBindBuffer(GL_ARRAY_BUFFER, buffers[counterT++]);
			glBufferData(GL_ARRAY_BUFFER, sizeof(float) * points.size() * 3, vertices, GL_STATIC_DRAW);
			free(vertices);
		}

	}
}

int main(int argc, char **argv) {
	if (argc < 2) {
		cout << "sintaxe : drawer <fileName>.xml\n";
		return -1;
	}
	string xmlFile = fileDir + argv[1];
	TiXmlDocument doc(xmlFile.c_str());
	if (!doc.LoadFile()) {
		cout << "erro a ler o ficheiro xml" << endl;
		return -2;
	}

	TiXmlElement* root = doc.RootElement();
	for (TiXmlElement* group = root->FirstChildElement("group"); group; group = group->NextSiblingElement())
		scene.push_back(calculateGroupPoints(group));
	

	t = (float*)malloc(sizeof(float)*nCatmmulls);
	for (int i = 0; i < nCatmmulls; i++)
		t[i] = 0;
	rot = (float*)malloc(sizeof(float)*nRots);
	for (int i = 0; i < nRots; i++)
		rot[i] = 0;
	// inicialization
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
	glutInitWindowPosition(100, 100);
	glutInitWindowSize(800, 800);
	glutCreateWindow("CG@DI-UM");

	// callback registration s
	glutDisplayFunc(renderScene);
	glutIdleFunc(renderScene);
	glutReshapeFunc(changeSize);

	glutKeyboardFunc(processKeyboard);
	// mouse callbacks
	glutMouseFunc(processMouseButtons);
	glutMotionFunc(processMouseMotion);

	// OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

	glewInit();

	buffers = (GLuint*)malloc(sizeof(GLuint)*nModels);
	glGenBuffers(nModels, buffers);


	counterT = 0;
	fillBuffers(scene);
	glEnableClientState(GL_VERTEX_ARRAY);


	// enter GLUT's main cycle 
	glutMainLoop();
	return 0;
}

