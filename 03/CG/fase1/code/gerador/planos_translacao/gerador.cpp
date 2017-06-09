#include "stdafx.h"
#include <string>
#include <iostream>
#include <fstream>
#include <sstream>

using namespace std;

string squarePlane(float size) {
	std::stringstream ret;
	float halfSize = size / 2;
	ret << -halfSize << " 0 " << halfSize << "\n";
	ret << halfSize << " 0 " << -halfSize << "\n";
	ret << -halfSize << " 0 " << -halfSize << "\n";

	ret << -halfSize << " 0 " << halfSize << "\n";
	ret << halfSize << " 0 " << halfSize << "\n";
	ret << halfSize << " 0 " << -halfSize << "\n";

	ret << "FIM";
	return ret.str();
}

string rectPlane(float sizeX , float sizeZ ) {
	std::stringstream ret;
	float halfSizeX = sizeX / 2;
	float halfSizeZ = sizeZ / 2;
	ret << -halfSizeX << " 0 " << halfSizeZ << "\n";
	ret << halfSizeX << " 0 " << -halfSizeZ << "\n";
	ret << -halfSizeX << " 0 " << -halfSizeZ << "\n";

	ret << -halfSizeX << " 0 " << halfSizeZ << "\n";
	ret << halfSizeX << " 0 " << halfSizeZ << "\n";
	ret << halfSizeX << " 0 " << -halfSizeZ << "\n";

	ret << "FIM";
	return ret.str();
}

string plane(float sizeX, float sizeY, float sizeZ , float coordX , float coordY , float coordZ) {
	std::stringstream ret;
	float halfSizeX = sizeX / 2;
	float halfSizeY = sizeY / 2;
	float halfSizeZ = sizeZ / 2;
		ret << coordX - halfSizeX << " " << coordY - halfSizeY << " " << coordZ + halfSizeZ << "\n";
		ret << coordX + halfSizeX << " " << coordY - halfSizeY << " " << coordZ - halfSizeZ << "\n";
		ret << coordX - halfSizeX << " " << coordY + halfSizeY << " " << coordZ - halfSizeZ << "\n";

		ret << coordX - halfSizeX << " " << coordY + halfSizeY << " " << coordZ + halfSizeZ << "\n";
		ret << coordX + halfSizeX << " " << coordY - halfSizeY << " " << coordZ + halfSizeZ << "\n";
		ret << coordX + halfSizeX << " " << coordY + halfSizeY << " " << coordZ - halfSizeZ << "\n";
	
	
	ret << "FIM";
	return ret.str();
}

string box(float xSize, float ySize, float zSize) {
	float x = xSize / 2;
	float y = ySize / 2;
	float z = zSize / 2;
	std::stringstream ret;
	// primeira face (z)
	ret << -x << " " << -y << " " << z << "\n" << x << " " << y << " " << z << "\n" << -x << " " << y << " " << z << "\n" <<
		-x << " " << -y << " " << z << "\n" << x << " " << -y << " " << z << "\n" << x << " " << y << " " << z << "\n" <<
		// segunda face  (x)

		x << " " << -y << " " << z << "\n" << x << " " << y << " " << -z << "\n" << x << " " << y << " " << z << "\n" <<
		x << " " << -y << " " << z << "\n" << x << " " << -y << " " << -z << "\n" << x << " " << y << " " << -z << "\n" <<
		// terceira face(-z)

		x << " " << -y << " " << -z << "\n" << -x << " " << y << " " << -z <<  "\n" << x << " " << y << " " << -z << "\n" <<
		x << " " << -y << " " << -z << "\n" << -x << " " << -y << " " << -z <<  "\n" << -x << " " << y << " " << -z << "\n" <<
		// quarta face (-x)

		-x << " " << -y << " " << -z << "\n" << -x << " " << y << " " << z << "\n" << -x << " " << y << " " << -z << "\n" <<
		-x << " " << -y << " " << -z << "\n" << -x << " " << -y << " " << z <<  "\n" << -x << " " << y << " " << z << "\n" <<
		// quinta face (y)

		-x << " " << y << " " << z << "\n" << x << " " << y << " " << -z << "\n" << -x << " " << y << " " << -z << "\n" <<
		-x << " " << y << " " << z << "\n" << x << " " << y << " " << z << "\n" << x << " " << y << " " << -z << "\n" <<
		// sexta face (-y)

		-x << " " << -y << " " << -z << "\n" << x << " " << -y << " " << z << "\n" << -x << " " << -y << " " << z << "\n" <<
		-x << " " << -y << " " << -z << "\n" << x << " " << -y << " " << -z << "\n" << x << " " << -y << " " << z << "\n";
	ret << "FIM";
	return ret.str();
}

int main(int argc, char* argv[]) {
	if (argc < 2) {
		printf("precisa de argumentos!\n");
		return 0;
	}
	string filename = argv[argc - 1];
	string destDir = "../../xml_3d/";

	ofstream myfile;

	if (strcmp(argv[1], "plane") == 0) {  //  PLANO
		if (argc < 4) {
			printf("sintaxe: gerador plane <size>\nOU:\nsintaxe: gerador plane <sizeX> <sizeZ> <fileName>\n");
			return 0;
		}
		if (argc == 4) {
			myfile.open(destDir + filename);
			myfile << squarePlane(stof(argv[2]));
			myfile.close();
		}
		if (argc == 5) {
			myfile.open(destDir + filename);
			myfile << rectPlane(stof(argv[2]), stof(argv[3]));
			myfile.close();
		}
		if (argc == 9) {
			myfile.open(destDir + filename);
			myfile << plane(stof(argv[2]), stof(argv[3]), stof(argv[4]), stof(argv[5]), stof(argv[6]), stof(argv[7]));
			myfile.close();
		}

	}
	if (strcmp(argv[1], "box") == 0) {   //  CAIXA
		if (argc < 6) {
			printf("sintaxe: gerador box <xSize> <ySize> <zSize> <fileName>\n");
			return 0;
		}
		myfile.open(destDir + filename);

		myfile << box(stof(argv[2]), stof(argv[3]), stof(argv[4]));
		myfile.close();

	}
	if (strcmp(argv[1], "sphere") == 0) {    //  ESFERA
		if (argc < 6) {
			printf("sintaxe: gerador sphere <radius> <slices> <stacks> <fileName>\n");
			return 0;
		}
		float radius = stof(argv[2]);
		int slices = stoi(argv[3]);
		int stacks = stoi(argv[4]);
		float v1x, v1y, v1z, v2x, v2y, v2z, v3x, v3y, v3z, v4x, v4y, v4z;
		float thetaAmt, phiAmt;
		float RADIANS = 3.14159f / 180.0f;

		if (slices < 1)thetaAmt = 1; else thetaAmt = (float)360 / (float)slices;
		if (stacks < 1)phiAmt = 1; else phiAmt = (float)180 / (float)stacks;
		myfile.open(destDir + filename);
		for (float phi = 0; phi < 180; phi += phiAmt) {
			for (float theta = -180; theta < 180; theta += thetaAmt) {
				v1x = radius*sin(phi*RADIANS)*cos(theta*RADIANS);
				v1y = radius*sin(phi*RADIANS)*sin(theta*RADIANS);
				v1z = radius*cos(phi*RADIANS);


				v2x = radius*sin((phi + phiAmt)*RADIANS)*cos(theta*RADIANS);
				v2y = radius*sin((phi + phiAmt)*RADIANS)*sin(theta*RADIANS);
				v2z = radius*cos((phi + phiAmt)*RADIANS);


				v3x = radius*sin(RADIANS*phi)*cos(RADIANS*(theta + thetaAmt));
				v3y = radius*sin(RADIANS*phi)*sin(RADIANS*(theta + thetaAmt));
				v3z = radius*cosf(RADIANS*phi);

				v4x = radius*sin(RADIANS*(phi + phiAmt))*cos(RADIANS*(theta + thetaAmt));
				v4y = radius*sin(RADIANS*(phi + phiAmt))*sin(RADIANS*(theta + thetaAmt));
				v4z = radius*cosf(RADIANS*(phi + phiAmt));

				myfile << v1x << " " << v1y << " " << v1z << "\n"
					<< v2x << " " << v2y << " " << v2z << "\n"
					<< v3x << " " << v3y << " " << v3z << "\n"
					<< v3x << " " << v3y << " " << v3z << "\n"
					<< v2x << " " << v2y << " " << v2z << "\n"
					<< v4x << " " << v4y << " " << v4z << "\n";
			}

		}
		myfile << "FIM";
		myfile.close();

	}
	if (strcmp(argv[1], "cone") == 0) {   //  CONE
		if (argc < 7) {
			printf("sintaxe: gerador cone <bottomRadius> <height> <slices> <stacks> <fileName>\n");
			return 0;
		}
		float radius, height, slices, stacks;

		float thetaAmt;
		float RADIANS = 3.14159f / 180;
		float tmpRadius, tmpRadiusNext;

		radius = stof(argv[2]);
		height = stof(argv[3]);
		slices = stof(argv[4]);
		stacks = stof(argv[5]);

		if (slices < 1)thetaAmt = 1; else thetaAmt = 360 / slices;

		float stacksVar;
		if (stacks > 0) stacksVar = (height / (stacks));
		else stacksVar = height;
		myfile.open(destDir + filename);
		/* circular face*/
		for (float theta = 0; theta <= 360; theta += thetaAmt) {

			for (float j = -0.5f*height; j < 0.5f*height; j += stacksVar) {
				tmpRadius = (height - (j + 0.5*height))*(radius / height);
				tmpRadiusNext = (height - ((j + 0.5*height) + stacksVar))*(radius / height);


				myfile << tmpRadius*cos(RADIANS * (theta)) << " " << j << " " << tmpRadius* sin(RADIANS * (theta)) << "\n" <<
					tmpRadius*cos(RADIANS * (theta + thetaAmt)) << " " << j << " " << tmpRadius*sin(RADIANS * (theta + thetaAmt)) << "\n" <<
					tmpRadiusNext*cos(RADIANS * (theta)) << " " << j + stacksVar << " " << tmpRadiusNext*sin(RADIANS * (theta)) << "\n" <<


					tmpRadiusNext*cos(RADIANS * (theta)) << " " << j + stacksVar << " " << tmpRadiusNext*sin(RADIANS * (theta)) << "\n" <<
					tmpRadius*cos(RADIANS * (theta + thetaAmt)) << " " << j << " " << tmpRadius*sin(RADIANS * (theta + thetaAmt)) << "\n" <<
					tmpRadiusNext*cos(RADIANS * (theta + thetaAmt)) << " " << j + stacksVar << " " << tmpRadiusNext*sin(RADIANS * (theta + thetaAmt)) << "\n";
			}

		}
		/* bottom circle */
		for (float theta = 0; theta <= 360; theta += thetaAmt) {
			myfile << "0 " << height*-0.5f << " 0" << "\n" <<
				radius*sin(RADIANS*(theta)) << " " << height*-0.5f << " " << radius*cos(RADIANS * (theta)) << "\n" <<
				radius*sin(RADIANS*(theta + thetaAmt)) << " " << height*-0.5f << " " << radius* cos(RADIANS*(theta + thetaAmt)) << "\n";
		}

		myfile << "FIM";
		myfile.close();

	}


	return 0;
}

