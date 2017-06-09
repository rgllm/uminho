#include <vector>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif
using namespace std;


class Point {
public:
	float x;
	float y;
	float z;
};

class Color {
public:
	float r;
	float g;
	float b;
};

class Model {
public:
	GLuint points;
	GLuint normals;
	GLuint textures;
	int nPoints;
	bool hasTexture;
	GLuint texID;
	Color diffusion;
};

class Group {
public:
	vector<Model> models;
	float translateTime;
	Point translation;
	vector<Point> controlPoints;
	Point rotation;
	Point scale;
	float rotateTime;
	vector<Group>childs;
	float deltaT;
};



class Light {
public:
	int type;
	Point position;
};