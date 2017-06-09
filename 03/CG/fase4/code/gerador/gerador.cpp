#include "stdafx.h"
#include <string>
#include <iostream>
#include<vector>
#include <fstream>
#include <sstream>
#define M_PI       3.14159265358979323846   // pi
#define M_PI_2     1.57079632679489661923   // pi/2

using namespace std;

string destDir = "../../xml_3d/";


// calculo das normais de um triangulo
float *normal(float p1[3], float p2[3], float p3[3])
{
	float *normal = NULL;
	normal = (float*)malloc(3 * sizeof(float));

	float v2[3] = { p3[0] - p1[0], p3[1] - p1[1], p3[2] - p1[2] };
	float v1[3] = { p2[0] - p1[0], p2[1] - p1[1], p2[2] - p1[2] };

	float v[3];
	v[0] = v1[1] * v2[2] - v1[2] * v2[1];
	v[1] = v1[2] * v2[0] - v1[0] * v2[2];
	v[2] = v1[0] * v2[1] - v1[1] * v2[0];

	float vn = sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);

	normal[0] = v[0] / vn;
	normal[1] = v[1] / vn;
	normal[2] = v[2] / vn;

	return normal;
}

string plane(float sizeX, float sizeZ) {
	std::stringstream ret;
	float halfSizeX = sizeX / 2;
	float halfSizeZ = sizeZ / 2;
	ret << -halfSizeX << " 0 " << halfSizeZ << "\n"
		<< "0 1 0" << "\n"
		<< "0 1" << "\n"
		<< halfSizeX << " 0 " << -halfSizeZ << "\n"
		<< "0 1 0" << "\n"
		<< "1 0" << "\n"
		<< -halfSizeX << " 0 " << -halfSizeZ << "\n"
		<< "0 1 0" << "\n"
		<< "0 0" << "\n"

		<< -halfSizeX << " 0 " << halfSizeZ << "\n"
		<< "0 1 0" << "\n"
		<< "0 1" << "\n"
		<< halfSizeX << " 0 " << halfSizeZ << "\n"
		<< "0 1 0" << "\n"
		<< "1 1" << "\n"
		<< halfSizeX << " 0 " << -halfSizeZ << "\n"
		<< "0 1 0" << "\n"
		<< "1 0" << "\n";

	ret << "FIM";
	return ret.str();
}



string box(float xSize, float ySize, float zSize, int divisions) {
	float x = xSize / 2;
	float y = ySize / 2;
	float z = zSize / 2;
	float deltaX = xSize / divisions;
	float deltaY = ySize / divisions;
	float deltaZ = zSize / divisions;

	std::stringstream ret;
	for (int i = 0; i <= divisions; ++i) {
		float coordX = -x + i*deltaX;
		// plano da esquerda (-x)
		if (i == 0) {
			for (int j = 0; j < divisions; j++) {
				float coordY = -y + j*deltaY;
				float coordY1 = -y + (j + 1)*deltaY;
				for (int k = 0; k < divisions; k++) {
					float coordZ = -z + k*deltaZ;
					float coordZ1 = -z + (k + 1)*deltaZ;
					string normal = "-1 0 0\n";
					ret << coordX << " " << coordY << " " << coordZ << "\n"
						<< normal
						<< coordX << " " << coordY1 << " " << coordZ1 << "\n"
						<< normal
						<< coordX << " " << coordY1 << " " << coordZ << "\n"
						<< normal

						<< coordX << " " << coordY << " " << coordZ << "\n"
						<< normal
						<< coordX << " " << coordY << " " << coordZ1 << "\n"
						<< normal
						<< coordX << " " << coordY1 << " " << coordZ1 << "\n"
						<< normal;
				}
			}
		}
		else {
			// plano da direita (x)
			if (i == divisions) {
				for (int j = 0; j < divisions; j++) {
					float coordY = -y + j*deltaY;
					float coordY1 = -y + (j + 1)*deltaY;
					for (int k = 0; k < divisions; k++) {
						float coordZ = -z + k*deltaZ;
						float coordZ1 = -z + (k + 1)*deltaZ;
						string normal = "1 0 0\n";
						ret << coordX << " " << coordY << " " << coordZ << "\n"
							<< normal
							<< coordX << " " << coordY1 << " " << coordZ << "\n"
							<< normal
							<< coordX << " " << coordY1 << " " << coordZ1 << "\n"
							<< normal

							<< coordX << " " << coordY << " " << coordZ1 << "\n"
							<< normal
							<< coordX << " " << coordY << " " << coordZ << "\n"
							<< normal
							<< coordX << " " << coordY1 << " " << coordZ1 << "\n"
							<< normal;
					}
				}
			}

			float coordX1 = -x + (i - 1)*deltaX;
			for (int j = 0; j < divisions; j++) {
				// planos frontal e traseiro (-z e +z)
				float coordY = -y + j*deltaY;
				float coordY1 = -y + (j + 1)*deltaY;
				float coordZ = -z + j*deltaZ;
				float coordZ1 = -z + (j + 1)*deltaZ;
				string normal = "0 0 -1\n";
				ret << coordX << " " << coordY1 << " " << -z << "\n"
					<< normal
					<< coordX << " " << coordY << " " << -z << "\n"
					<< normal
					<< coordX1 << " " << coordY << " " << -z << "\n"
					<< normal

					<< coordX1 << " " << coordY << " " << -z << "\n"
					<< normal
					<< coordX1 << " " << coordY1 << " " << -z << "\n"
					<< normal
					<< coordX << " " << coordY1 << " " << -z << "\n"
					<< normal;

				normal = "0 0 1\n";
				ret << coordX << " " << coordY << " " << z << "\n"
					<< normal
					<< coordX << " " << coordY1 << " " << z << "\n"
					<< normal
					<< coordX1 << " " << coordY << " " << z << "\n"
					<< normal

					<< coordX1 << " " << coordY1 << " " << z << "\n"
					<< normal
					<< coordX1 << " " << coordY << " " << z << "\n"
					<< normal
					<< coordX << " " << coordY1 << " " << z << "\n"
					<< normal;

				// planos superior e inferior (-y e +y)
				normal = "0 -1 0\n";
				ret << coordX << " " << -y << " " << coordZ << "\n"
					<< normal
					<< coordX << " " << -y << " " << coordZ1 << "\n"
					<< normal
					<< coordX1 << " " << -y << " " << coordZ << "\n"
					<< normal

					<< coordX << " " << -y << " " << coordZ1 << "\n"
					<< normal
					<< coordX1 << " " << -y << " " << coordZ1 << "\n"
					<< normal
					<< coordX1 << " " << -y << " " << coordZ << "\n"
					<< normal;

				normal = "0 1 0\n";
				ret << coordX << " " << y << " " << coordZ1 << "\n"
					<< normal
					<< coordX << " " << y << " " << coordZ << "\n"
					<< normal
					<< coordX1 << " " << y << " " << coordZ << "\n"
					<< normal

					<< coordX1 << " " << y << " " << coordZ1 << "\n"
					<< normal
					<< coordX << " " << y << " " << coordZ1 << "\n"
					<< normal
					<< coordX1 << " " << y << " " << coordZ << "\n"
					<< normal;
			}
		}
	}
	ret << "FIM";
	return ret.str();
}

string sphere(float radius, int slices, int stacks) {
	float deltaBeta = (float)M_PI / stacks;
	float deltaAlpha = 2 * (float)M_PI / slices;
	float beta, alpha;
	float p1x, p1y, p1z, p2x, p2y, p2z, p3x, p3y, p3z, p4x, p4y, p4z;
	float n1x, n1y, n1z, n2x, n2y, n2z, n3x, n3y, n3z, n4x, n4y, n4z;
	float t1x, t1z, t2x, t2z, t3x, t3z, t4x, t4z;

	stringstream ret;
	for (int i = 0; i < stacks; i++) {
		beta = i*deltaBeta;
		for (int j = 0; j < slices; j++) {
			alpha = j*deltaAlpha;
			p1x = radius*sin(beta)*cos(alpha);
			p1y = radius*sin(beta)*sin(alpha);
			p1z = radius*cos(beta);

			n1x = sin(beta)*cos(alpha);
			n1y = sin(beta)*sin(alpha);
			n1z = cos(beta);

			t1x =  alpha / (2 * M_PI);
			t1z =  beta / ( M_PI);

			p2x = radius*sin((beta + deltaBeta))*cos(alpha);
			p2y = radius*sin((beta + deltaBeta))*sin(alpha);
			p2z = radius*cos((beta + deltaBeta));

			n2x = sin((beta + deltaBeta))*cos(alpha);
			n2y = sin((beta + deltaBeta))*sin(alpha);
			n2z = cos((beta + deltaBeta));

			t2x = alpha  / (2 * M_PI);
			t2z = (beta+deltaBeta) / (M_PI);

			p3x = radius*sin(beta)*cos((alpha + deltaAlpha));
			p3y = radius*sin(beta)*sin((alpha + deltaAlpha));
			p3z = radius*cos(beta);

			n3x = sin(beta)*cos((alpha + deltaAlpha));
			n3y = sin(beta)*sin((alpha + deltaAlpha));
			n3z = cos(beta);

			t3x = (alpha + deltaAlpha ) / (2 * M_PI);
			t3z = beta / (M_PI);

			p4x = radius*sin((beta + deltaBeta))*cos((alpha + deltaAlpha));
			p4y = radius*sin((beta + deltaBeta))*sin((alpha + deltaAlpha));
			p4z = radius*cos((beta + deltaBeta));

			n4x = sin((beta + deltaBeta))*cos((alpha + deltaAlpha));
			n4y = sin((beta + deltaBeta))*sin((alpha + deltaAlpha));
			n4z = cos((beta + deltaBeta));

			t4x = (alpha + deltaAlpha) / (2 * M_PI);
			t4z = (beta + deltaBeta) / ( M_PI);

			ret << p1x << " " << p1y << " " << p1z << "\n"
				<< n1x << " " << n1y << " " << n1z << "\n"
				<< t1x << " " << t1z << "\n"
				<< p2x << " " << p2y << " " << p2z << "\n"
				<< n2x << " " << n2y << " " << n2z << "\n"
				<< t2x << " " << t2z << "\n"
				<< p3x << " " << p3y << " " << p3z << "\n"
				<< n3x << " " << n3y << " " << n3z << "\n"
				<< t3x << " " << t3z << "\n"
				
				<< p3x << " " << p3y << " " << p3z << "\n"
				<< n3x << " " << n3y << " " << n3z << "\n"
				<< t3x << " " << t3z << "\n"
				<< p2x << " " << p2y << " " << p2z << "\n"
				<< n2x << " " << n2y << " " << n2z << "\n"
				<< t2x << " " << t2z << "\n"
				<< p4x << " " << p4y << " " << p4z << "\n"
				<< n4x << " " << n4y << " " << n4z << "\n"
				<< t4x << " " << t4z << "\n";
		}
	}
	ret << "FIM";
	return ret.str();
}

string cone(float radius, float height, int slices, int stacks) {
	float deltaHeight = height / stacks;
	float deltaAlpha = 2 * (float)M_PI / slices;
	float deltaRadius = radius / stacks;
	float alpha, heightAux, radiusAux, radiusNext;
	stringstream ret;
	for (int i = 0; i < stacks; i++) {
		heightAux = i*deltaHeight;
		radiusAux = radius - i*deltaRadius;
		radiusNext = radius - (i + 1)*deltaRadius;
		for (int j = 0; j < slices; j++) {
			alpha = j*deltaAlpha;
			float cosAlpha = cos(alpha);
			float cosNextAlpha = cos(alpha + deltaAlpha);
			float sinAlpha = sin(alpha);
			float sinNextAlpha = sin(alpha + deltaAlpha);

			float p1[3] = { radiusAux*cosAlpha , heightAux , radiusAux*sinAlpha };
			float p2[3] = { radiusNext*cosNextAlpha , heightAux + deltaHeight , radiusNext*sinNextAlpha };
			float p3[3] = { radiusAux*cosNextAlpha , heightAux , radiusAux*sinNextAlpha };
			float p4[3] = { radiusNext*cosAlpha , heightAux + deltaHeight , radiusNext*sinAlpha };

			float *n1 = normal(p1, p2, p3);
			float *n2 = normal(p2, p3, p1);
			float *n3 = normal(p3, p1, p2);

			float *n4 = normal(p1, p4, p2);
			float *n5 = normal(p4, p2, p1);
			float *n6 = normal(p2, p1, p4);

			ret << p1[0] << " " << p1[1] << " " << p1[2] << "\n"
				<< n1[0] << " " << n1[1] << " " << n1[2] << "\n"
				<< p2[0] << " " << p2[1] << " " << p2[2] << "\n"
				<< n2[0] << " " << n2[1] << " " << n2[2] << "\n"
				<< p3[0] << " " << p3[1] << " " << p3[2] << "\n"
				<< n3[0] << " " << n3[1] << " " << n3[2] << "\n"

				<< p1[0] << " " << p1[1] << " " << p1[2] << "\n"
				<< n4[0] << " " << n4[1] << " " << n4[2] << "\n"
				<< p4[0] << " " << p4[1] << " " << p4[2] << "\n"
				<< n5[0] << " " << n5[1] << " " << n5[2] << "\n"
				<< p2[0] << " " << p2[1] << " " << p2[2] << "\n"
				<< n6[0] << " " << n6[1] << " " << n6[2] << "\n";
		}
	}
	// base do cone
	string normal = "0 -1 0\n";
	for (int i = 0; i < slices; i++) {
		alpha = i*deltaAlpha;
		ret << "0" << " 0 " << "0" << "\n"
			<< normal
			<< radius*cos(alpha) << " 0 " << radius*sin(alpha) << "\n"
			<< normal
			<< radius*cos(alpha + deltaAlpha) << " 0 " << radius*sin(alpha + deltaAlpha) << "\n"
			<< normal;
	}
	ret << "FIM";
	return ret.str();
}



float getBezierPoint(float u, float v, int coord, float ** vertices, int * indices) {
	float pointValue = 0;

	float bu[4][1] = { { powf(1 - u, 3) },{ 3 * u * powf(1 - u, 2) },{ 3 * powf(u, 2) * (1 - u) },{ powf(u, 3) } };
	float bv[4][1] = { { powf(1 - v, 3) },{ 3 * v * powf(1 - v, 2) },{ 3 * powf(v, 2) * (1 - v) },{ powf(v, 3) } };

	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {
			pointValue += vertices[indices[j + 4 * i]][coord] * bu[i][0] * bv[j][0];
		}
	}
	return pointValue;
}



string renderPatch(int points, float ** controlPoints, int patches, int ** indices, float tessel) {
	float p1[3], p2[3], p3[3], p4[3];
	float v, u;
	stringstream ret;
	float tesselation = 1.0f / tessel;
	for (int patch = 0; patch < patches; patch++) {
		int * indicesPatch = indices[patch];
		for (v = 0; v < 1; v += tesselation) {
			for (u = 0; u < 1; u += tesselation) {
				p1[0] = getBezierPoint(u, v, 0, controlPoints, indicesPatch);
				p1[1] = getBezierPoint(u, v, 1, controlPoints, indicesPatch);
				p1[2] = getBezierPoint(u, v, 2, controlPoints, indicesPatch);

				p2[0] = getBezierPoint(u + tesselation, v, 0, controlPoints, indicesPatch);
				p2[1] = getBezierPoint(u + tesselation, v, 1, controlPoints, indicesPatch);
				p2[2] = getBezierPoint(u + tesselation, v, 2, controlPoints, indicesPatch);

				p3[0] = getBezierPoint(u, v + tesselation, 0, controlPoints, indicesPatch);
				p3[1] = getBezierPoint(u, v + tesselation, 1, controlPoints, indicesPatch);
				p3[2] = getBezierPoint(u, v + tesselation, 2, controlPoints, indicesPatch);

				p4[0] = getBezierPoint(u + tesselation, v + tesselation, 0, controlPoints, indicesPatch);
				p4[1] = getBezierPoint(u + tesselation, v + tesselation, 1, controlPoints, indicesPatch);
				p4[2] = getBezierPoint(u + tesselation, v + tesselation, 2, controlPoints, indicesPatch);

				ret << p1[0] << " " << p1[1] << " " << p1[2] << "\n";
				ret << p2[0] << " " << p2[1] << " " << p2[2] << "\n";
				ret << p3[0] << " " << p3[1] << " " << p3[2] << "\n";
				ret << p3[0] << " " << p3[1] << " " << p3[2] << "\n";
				ret << p2[0] << " " << p2[1] << " " << p2[2] << "\n";
				ret << p4[0] << " " << p4[1] << " " << p4[2] << "\n";
			}
		}
	}
	ret << "FIM";
	return ret.str();
}

string patches(string fileIn, float tesselLvl) {
	string line;
	string indice[16];
	ifstream infile;
	int i, j, patches, points, **indices;
	float **controlPoints;
	infile.open(destDir + fileIn);

	getline(infile, line);

	patches = stoi(line);

	indices = (int **)malloc(sizeof(int*)*patches);
	for (i = 0; i < patches; i++) {

		indices[i] = (int *)malloc(sizeof(int) * 16);
	}

	for (i = 0; i < patches; i++) {
		getline(infile, line);
		std::istringstream ss(line);
		std::string token;
		j = 0;
		while (std::getline(ss, token, ',')) {
			indices[i][j] = stoi(token);
			j++;
		}
	}
	getline(infile, line);
	points = stoi(line);
	controlPoints = (float **)malloc(sizeof(float*)*points);
	for (i = 0; i < points; i++) {
		controlPoints[i] = (float *)malloc(sizeof(float) * 3);
	}
	for (i = 0; i < points; i++) {
		getline(infile, line);
		std::istringstream ss(line);
		std::string token;
		j = 0;
		while (std::getline(ss, token, ',')) {
			controlPoints[i][j] = stof(token);
			j++;
		}
	}
	return renderPatch(points, controlPoints, patches, indices, tesselLvl);
}


int main(int argc, char* argv[]) {
	if (argc < 2) {
		printf("precisa de argumentos:\n-gerador plane\n-gerador box\n-gerador sphere\n-gerador cone\n-gerador patch\n");
		return -1;
	}
	string filename = argv[argc - 1];

	ofstream myfile;
	if (strcmp(argv[1], "plane") == 0) {  //  PLANO
		if (argc < 4 || argc > 5) {
			printf("sintaxe: gerador plane <size> <fileName>\nOU:\nsintaxe: gerador plane <sizeX> <sizeZ> <fileName>\n");
			return -1;
		}
		myfile.open(destDir + filename);
		if (argc == 4)
			myfile << plane(stof(argv[2]), stof(argv[2]));
		if (argc == 5)
			myfile << plane(stof(argv[2]), stof(argv[3]));
	}
	if (strcmp(argv[1], "box") == 0) {   //  CAIXA
		if (argc < 6 || argc >7) {
			printf("sintaxe: gerador box <xSize> <ySize> <zSize> <fileName>\nOU:\nsintaxe: gerador box <xSize> <ySize> <zSize> <divisions> <fileName>\n");
			return -1;
		}
		myfile.open(destDir + filename);
		if (argc == 6)
			myfile << box(stof(argv[2]), stof(argv[3]), stof(argv[4]), 1);
		if (argc == 7)
			myfile << box(stof(argv[2]), stof(argv[3]), stof(argv[4]), stoi(argv[5]));

	}
	if (strcmp(argv[1], "sphere") == 0) {    //  ESFERA
		if (argc != 6) {
			printf("sintaxe: gerador sphere <radius> <slices> <stacks> <fileName>\n");
			return -1;
		}
		myfile.open(destDir + filename);
		myfile << sphere(stof(argv[2]), stoi(argv[3]), stoi(argv[4]));
	}
	if (strcmp(argv[1], "cone") == 0) {   //  CONE
		if (argc != 7) {
			printf("sintaxe: gerador cone <bottomRadius> <height> <slices> <stacks> <fileName>\n");
			return -1;
		}
		myfile.open(destDir + filename);
		myfile << cone(stof(argv[2]), stof(argv[3]), stoi(argv[4]), stoi(argv[5]));
	}
	if (strcmp(argv[1], "patch") == 0) {
		if (argc != 5) {
			printf("sintaxe: gerador patch <patchFile> <tesselation> <fileName>\n");
			return -1;
		}
		myfile.open(destDir + filename);
		myfile << patches(argv[2], stof(argv[3]));
	}
	myfile.close();


	return 0;
}

