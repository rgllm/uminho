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


string plane(double sizeX, double sizeZ) {
	std::stringstream ret;
	double halfSizeX = sizeX / 2;
	double halfSizeZ = sizeZ / 2;
	ret << -halfSizeX << " 0 " << halfSizeZ  << "\n"
		<< halfSizeX  << " 0 " << -halfSizeZ << "\n"
		<< -halfSizeX << " 0 " << -halfSizeZ << "\n"

		<< -halfSizeX << " 0 " << halfSizeZ  << "\n"
		<< halfSizeX  << " 0 " << halfSizeZ  << "\n"
		<< halfSizeX  << " 0 " << -halfSizeZ << "\n";

	ret << "FIM";
	return ret.str();
}



string box(double xSize, double ySize, double zSize, int divisions) {
	double x = xSize / 2;
	double y = ySize / 2;
	double z = zSize / 2;
	double deltaX = xSize / divisions;
	double deltaY = ySize / divisions;
	double deltaZ = zSize / divisions;

	std::stringstream ret;
	for (int i = 0; i <= divisions; ++i) {
		double coordX = -x + i*deltaX;
		// primeira face (z)
		if (i == 0) {
			for (int j = 0; j < divisions; j++) {
				double coordY = -y + j*deltaY;
				double coordY1 = -y + (j + 1)*deltaY;
				for (int k = 0; k < divisions; k++) {
					double coordZ = -z + k*deltaZ;
					double coordZ1 = -z + (k + 1)*deltaZ;
					ret << coordX << " " << coordY  << " "  << coordZ << "\n"
						<< coordX << " " << coordY1 << " " << coordZ1 << "\n"
						<< coordX << " " << coordY1 << " " << coordZ  << "\n"

						<< coordX << " " << coordY  << " "  << coordZ  << "\n"
						<< coordX << " " << coordY  << " "  << coordZ1 << "\n"
						<< coordX << " " << coordY1 << " " << coordZ1  << "\n";
				}
			}
		}
		else {
			if (i == divisions) {
				for (int j = 0; j < divisions; j++) {
					double coordY = -y + j*deltaY;
					double coordY1 = -y + (j + 1)*deltaY;
					for (int k = 0; k < divisions; k++) {
						double coordZ = -z + k*deltaZ;
						double coordZ1 = -z + (k + 1)*deltaZ;
						ret << coordX << " " << coordY  << " " << coordZ  << "\n"
							<< coordX << " " << coordY1 << " " << coordZ  << "\n"
							<< coordX << " " << coordY1 << " " << coordZ1 << "\n"

							<< coordX << " " << coordY  << " " << coordZ1 << "\n"
							<< coordX << " " << coordY  << " " << coordZ  << "\n"
							<< coordX << " " << coordY1 << " " << coordZ1 << "\n";
					}
				}
			}

			double coordX1 = -x + (i - 1)*deltaX;
			for (int j = 0; j < divisions; j++) {
				// faces laterais
				double coordY = -y + j*deltaY;
				double coordY1 = -y + (j + 1)*deltaY;
				double coordZ = -z + j*deltaZ;
				double coordZ1 = -z + (j + 1)*deltaZ;

				ret << coordX  << " " << coordY1 << " " << -z << "\n"
					<< coordX  << " " << coordY  << " " << -z << "\n"
					<< coordX1 << " " << coordY  << " " << -z << "\n"

					<< coordX1 << " " << coordY  << " " << -z << "\n"
					<< coordX1 << " " << coordY1 << " " << -z << "\n"
					<< coordX  << " " << coordY1 << " " << -z << "\n"

					<< coordX  << " " << coordY  << " " << z << "\n"
					<< coordX  << " " << coordY1 << " " << z << "\n"
					<< coordX1 << " " << coordY  << " " << z << "\n"

					<< coordX1 << " " << coordY1 << " " << z << "\n"
					<< coordX1 << " " << coordY  << " " << z << "\n"
					<< coordX  << " " << coordY1 << " " << z << "\n"

					// faces superior e inferior
					<< coordX  << " " << -y << " " << coordZ  << "\n"
					<< coordX  << " " << -y << " " << coordZ1 << "\n"
					<< coordX1 << " " << -y << " " << coordZ  << "\n"

					<< coordX  << " " << -y << " " << coordZ1 << "\n"
					<< coordX1 << " " << -y << " " << coordZ1 << "\n"
					<< coordX1 << " " << -y << " " << coordZ  << "\n"

					<< coordX  << " " << y << " " << coordZ1 << "\n"
					<< coordX  << " " << y << " " << coordZ  << "\n"
					<< coordX1 << " " << y << " " << coordZ  << "\n"

					<< coordX1 << " " << y << " " << coordZ1 << "\n"
					<< coordX  << " " << y << " " << coordZ1 << "\n"
					<< coordX1 << " " << y << " " << coordZ  << "\n";
			}
		}
	}
	ret << "FIM";
	return ret.str();
}

string sphere(double radius, int slices, int stacks) {
	double deltaBeta = M_PI / stacks;
	double deltaAlpha = 2 * M_PI / slices;
	double beta, alpha;
	double p1x, p1y, p1z, p2x, p2y, p2z, p3x, p3y, p3z, p4x, p4y, p4z;
	stringstream ret;
	for (int i = 0; i < stacks; i++) {
		beta = i*deltaBeta;
		for (int j = 0; j < slices; j++) {
			alpha = j*deltaAlpha;
			p1x = radius*sin(beta)*cos(alpha);
			p1y = radius*sin(beta)*sin(alpha);
			p1z = radius*cos(beta);

			p2x = radius*sin((beta + deltaBeta))*cos(alpha);
			p2y = radius*sin((beta + deltaBeta))*sin(alpha);
			p2z = radius*cos((beta + deltaBeta));

			p3x = radius*sin(beta)*cos((alpha + deltaAlpha));
			p3y = radius*sin(beta)*sin((alpha + deltaAlpha));
			p3z = radius*cos(beta);

			p4x = radius*sin((beta + deltaBeta))*cos((alpha + deltaAlpha));
			p4y = radius*sin((beta + deltaBeta))*sin((alpha + deltaAlpha));
			p4z = radius*cos((beta + deltaBeta));

			ret << p1x << " " << p1y << " " << p1z << "\n"
				<< p2x << " " << p2y << " " << p2z << "\n"
				<< p3x << " " << p3y << " " << p3z << "\n"
				<< p3x << " " << p3y << " " << p3z << "\n"
				<< p2x << " " << p2y << " " << p2z << "\n"
				<< p4x << " " << p4y << " " << p4z << "\n";
		}
	}
	ret << "FIM";
	return ret.str();
}

string cone(double radius, double height, int slices, int stacks) {
	double deltaHeight = height / stacks;
	double deltaAlpha = 2 * M_PI / slices;
	double deltaRadius = radius / stacks;
	double alpha, heightAux, radiusAux, radiusNext;
	stringstream ret;
	for (int i = 0; i < stacks; i++) {
		heightAux = i*deltaHeight;
		radiusAux = radius - i*deltaRadius;
		radiusNext = radius - (i + 1)*deltaRadius;
		for (int j = 0; j < slices; j++) {
			alpha = j*deltaAlpha;
			double cosAlpha= cos(alpha);
			double cosNextAlpha = cos(alpha + deltaAlpha);
			double sinAlpha = sin(alpha);
			double sinNextAlpha = sin(alpha + deltaAlpha);
			
			ret << radiusAux*cosAlpha		<< " " << heightAux               << " " << radiusAux*sinAlpha		<< "\n"
				<< radiusNext*cosNextAlpha	<< " " << heightAux + deltaHeight << " " << radiusNext*sinNextAlpha << "\n"
				<< radiusAux*cosNextAlpha	<< " " << heightAux               << " " << radiusAux*sinNextAlpha	<< "\n"

				<< radiusAux*cosAlpha		<< " " << heightAux               << " " << radiusAux*sinAlpha		<< "\n"
				<< radiusNext*cosAlpha		<< " " << heightAux + deltaHeight << " " << radiusNext*sinAlpha		<< "\n"
				<< radiusNext*cosNextAlpha	<< " " << heightAux + deltaHeight << " " << radiusNext*sinNextAlpha << "\n";
		}
	}
	for (int i = 0; i < slices; i++) {
		alpha = i*deltaAlpha;
		ret << "0"								<< " 0 " <<	"0"								<< "\n"
			<< radius*cos(alpha)				<< " 0 " << radius*sin(alpha)				<< "\n"
			<< radius*cos(alpha + deltaAlpha)	<< " 0 " << radius*sin(alpha + deltaAlpha)	<< "\n";
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
	float ponto[3];
	float v, u;
	stringstream ret;
	float tesselation = 1.0 / tessel;
	for (int patch = 0; patch < patches; patch++) {
		int * indicesPatch = indices[patch];
		for (v = 0; v < 1; v += tesselation) {
			for (u = 0; u < 1; u += tesselation) {
				ponto[0] = getBezierPoint(u, v, 0, controlPoints, indicesPatch);
				ponto[1] = getBezierPoint(u, v, 1, controlPoints, indicesPatch);
				ponto[2] = getBezierPoint(u, v, 2, controlPoints, indicesPatch);
				ret << ponto[0] << " " << ponto[1] << " " << ponto[2] << "\n";
				ponto[0] = getBezierPoint(u + tesselation, v, 0, controlPoints, indicesPatch);
				ponto[1] = getBezierPoint(u + tesselation, v, 1, controlPoints, indicesPatch);
				ponto[2] = getBezierPoint(u + tesselation, v, 2, controlPoints, indicesPatch);
				ret << ponto[0] << " " << ponto[1] << " " << ponto[2] << "\n";
				ponto[0] = getBezierPoint(u, v + tesselation, 0, controlPoints, indicesPatch);
				ponto[1] = getBezierPoint(u, v + tesselation, 1, controlPoints, indicesPatch);
				ponto[2] = getBezierPoint(u, v + tesselation, 2, controlPoints, indicesPatch);
				ret << ponto[0] << " " << ponto[1] << " " << ponto[2] << "\n";
				ponto[0] = getBezierPoint(u, v + tesselation, 0, controlPoints, indicesPatch);
				ponto[1] = getBezierPoint(u, v + tesselation, 1, controlPoints, indicesPatch);
				ponto[2] = getBezierPoint(u, v + tesselation, 2, controlPoints, indicesPatch);
				ret << ponto[0] << " " << ponto[1] << " " << ponto[2] << "\n";
				ponto[0] = getBezierPoint(u + tesselation, v, 0, controlPoints, indicesPatch);
				ponto[1] = getBezierPoint(u + tesselation, v, 1, controlPoints, indicesPatch);
				ponto[2] = getBezierPoint(u + tesselation, v, 2, controlPoints, indicesPatch);
				ret << ponto[0] << " " << ponto[1] << " " << ponto[2] << "\n";
				ponto[0] = getBezierPoint(u + tesselation, v + tesselation, 0, controlPoints, indicesPatch);
				ponto[1] = getBezierPoint(u + tesselation, v + tesselation, 1, controlPoints, indicesPatch);
				ponto[2] = getBezierPoint(u + tesselation, v + tesselation, 2, controlPoints, indicesPatch);
				ret << ponto[0] << " " << ponto[1] << " " << ponto[2] << "\n";
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
	infile.open(destDir+fileIn);

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
		printf("precisa de argumentos!\n");
		return -1;
	}
	string filename = argv[argc - 1];

	ofstream myfile;
	myfile.open(destDir + filename);
	if (strcmp(argv[1], "plane") == 0) {  //  PLANO
		if (argc < 4 || argc > 5) {
			printf("sintaxe: gerador plane <size> <fileName>\nOU:\nsintaxe: gerador plane <sizeX> <sizeZ> <fileName>\n");
			return -1;
		}
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
		myfile << sphere(stof(argv[2]), stoi(argv[3]), stoi(argv[4]));
	}
	if (strcmp(argv[1], "cone") == 0) {   //  CONE
		if (argc != 7) {
			printf("sintaxe: gerador cone <bottomRadius> <height> <slices> <stacks> <fileName>\n");
			return -1;
		}
		myfile << cone(stof(argv[2]), stof(argv[3]), stoi(argv[4]), stoi(argv[5]));
	}
	if (strcmp(argv[1], "patch") == 0) {
		if (argc != 5) {
			printf("sintaxe: gerador patch <patchFile> <tesselation> <fileName>\n");
			return -1;
		}
		myfile << patches(argv[2], stof(argv[3]));
	}
	myfile.close();


	return 0;
}

