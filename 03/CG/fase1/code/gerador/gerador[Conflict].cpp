#include "stdafx.h"
#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#define M_PI       3.14159265358979323846   // pi
#define M_PI_2     1.57079632679489661923   // pi/2

using namespace std;

string squarePlane(float size) {
	std::stringstream ret;
	float halfSize = size / 2;
	ret << -halfSize << " 0 " << halfSize  << "\n"
		<< halfSize  << " 0 " << -halfSize << "\n"
		<< -halfSize << " 0 " << -halfSize << "\n"

		<< -halfSize << " 0 " << halfSize  << "\n"
		<< halfSize  << " 0 " << halfSize  << "\n"
		<< halfSize  << " 0 " << -halfSize << "\n";

	ret << "FIM";
	return ret.str();
}

string rectPlane(float sizeX, float sizeZ) {
	std::stringstream ret;
	float halfSizeX = sizeX / 2;
	float halfSizeZ = sizeZ / 2;
	ret << -halfSizeX << " 0 " << halfSizeZ  << "\n"
		<< halfSizeX  << " 0 " << -halfSizeZ << "\n"
		<< -halfSizeX << " 0 " << -halfSizeZ << "\n"

		<< -halfSizeX << " 0 " << halfSizeZ  << "\n"
		<< halfSizeX  << " 0 " << halfSizeZ  << "\n"
		<< halfSizeX  << " 0 " << -halfSizeZ << "\n";

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
		// primeira face (z)
		if (i == 0) {
			for (int j = 0; j < divisions; j++) {
				float coordY = -y + j*deltaY;
				float coordY1 = -y + (j + 1)*deltaY;
				for (int k = 0; k < divisions; k++) {		
					float coordZ = -z + k*deltaZ;
					float coordZ1 = -z + (k + 1)*deltaZ;

					ret << coordX << " " << coordY	<< " " << coordZ  << "\n"
						<< coordX << " " << coordY1 << " " << coordZ1 << "\n"
						<< coordX << " " << coordY1 << " " << coordZ  << "\n"

						<< coordX << " " << coordY	<< " " << coordZ  << "\n"
						<< coordX << " " << coordY	<< " " << coordZ1 << "\n"
						<< coordX << " " << coordY1 << " " << coordZ1 << "\n";
				}
			}
		}
		else {
			if (i == divisions) {
				for (int j = 0; j < divisions; j++) {
					float coordY = -y + j*deltaY;
					float coordY1 = -y + (j + 1)*deltaY;
					for (int k = 0; k < divisions; k++) {
						float coordZ = -z + k*deltaZ;
						float coordZ1 = -z + (k + 1)*deltaZ;
						ret << coordX << " " << coordY  << " " << coordZ  << "\n"
							<< coordX << " " << coordY1 << " " << coordZ  << "\n"
							<< coordX << " " << coordY1 << " " << coordZ1 << "\n"

							<< coordX << " " << coordY  << " " << coordZ1 << "\n"
							<< coordX << " " << coordY  << " " << coordZ  << "\n"
							<< coordX << " " << coordY1 << " " << coordZ1 << "\n";
					}
				}
			}
			
			float coordX1 = -x + (i - 1)*deltaX;
			for (int j = 0; j < divisions; j++) {
				// faces laterais

				float coordY = -y + j*deltaY;
				float coordY1 = -y + (j + 1)*deltaY;
				float coordZ = -z + j*deltaZ;
				float coordZ1 = -z + (j + 1)*deltaZ;

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

string sphere(float radius, int slices, int stacks) {
	float deltaBeta = M_PI / stacks;
	float deltaAlpha = 2*M_PI / slices;
	float beta, alpha, nextBeta, nextAlpha;
	float p1x, p1y, p1z, p2x, p2y, p2z, p3x, p3y, p3z, p4x, p4y, p4z;
	stringstream ret;
	for (int i = 0; i < stacks; i++) {
		beta = i*deltaBeta;
		nextBeta = beta + deltaBeta;
		for (int j = 0; j < slices; j++) {
			alpha = j*deltaAlpha;
			nextAlpha = alpha + deltaAlpha;
			
			p1x = radius*sin(beta)*cos(alpha);
			p1y = radius*sin(beta)*sin(alpha);
			p1z = radius*cos(beta);

			p2x = radius*sin(nextBeta)*cos(alpha);
			p2y = radius*sin(nextBeta)*sin(alpha);
			p2z = radius*cos(nextBeta);


			p3x = radius*sin(beta)*cos(nextAlpha);
			p3y = radius*sin(beta)*sin(nextAlpha);
			p3z = radius*cosf(beta);

			p4x = radius*sin(nextBeta)*cos(nextAlpha);
			p4y = radius*sin(nextBeta)*sin(nextAlpha);
			p4z = radius*cosf(nextBeta);

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


	}
	if (strcmp(argv[1], "box") == 0) {   //  CAIXA
		if (argc < 6) {
			printf("sintaxe: gerador box <xSize> <ySize> <zSize> <fileName>\nOU:\nsintaxe: gerador box <xSize> <ySize> <zSize> <divisions> fileName>\n");
			return 0;
		}
		if (argc == 6) {
			myfile.open(destDir + filename);
			myfile << box(stof(argv[2]), stof(argv[3]), stof(argv[4]), 1);
			myfile.close();
		}
		if (argc == 7) {
			myfile.open(destDir + filename);
			myfile << box(stof(argv[2]), stof(argv[3]), stof(argv[4]), stoi(argv[5]));
			myfile.close();
		}

	}
	if (strcmp(argv[1], "sphere") == 0) {    //  ESFERA
		if (argc < 6) {
			printf("sintaxe: gerador sphere <radius> <slices> <stacks> <fileName>\n");
			return 0;
		}
			myfile.open(destDir + filename);
			myfile << sphere(stof(argv[2]), stoi(argv[3]), stoi(argv[4]));
			myfile.close();

		/*
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
		*/
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
		float top = 0.5f*height;
		float thetaR, nextThetaR;
		for (float theta = 0; theta <= 360; theta += thetaAmt) {
			for (float j = -top; j < top; j += stacksVar) {
				tmpRadius = (height - (j + top))*(radius / height);
				tmpRadiusNext = (height - ((j + top) + stacksVar))*(radius / height);
				thetaR = RADIANS * theta;
				nextThetaR = RADIANS * (theta + thetaAmt);

				myfile << tmpRadius*cos(thetaR)		 << " " << j			 << " " << tmpRadius* sin(thetaR)		<< "\n"
					<< tmpRadius*cos(nextThetaR)	 << " " << j			 << " " << tmpRadius*sin(nextThetaR)	<< "\n"
					<< tmpRadiusNext*cos(thetaR)	 << " " << j + stacksVar << " " << tmpRadiusNext*sin(thetaR)	<< "\n"
					
					<< tmpRadiusNext*cos(thetaR)	 << " " << j + stacksVar << " " << tmpRadiusNext*sin(thetaR)	<< "\n"
					<< tmpRadius*cos(nextThetaR)	 << " " << j			 << " " << tmpRadius*sin(nextThetaR)	<< "\n"
					<< tmpRadiusNext*cos(nextThetaR) << " " << j + stacksVar << " " << tmpRadiusNext*sin(nextThetaR) << "\n";
			}

		}
		/* bottom circle */
		for (float theta = 0; theta <= 360; theta += thetaAmt) {
			thetaR = RADIANS * theta;
			nextThetaR = RADIANS * (theta + thetaAmt);
			myfile	<< "0 "						<< -top	<< " 0"										<< "\n"
					<< radius*sin(thetaR)		<< " "	<< -top << " "	<< radius*cos(thetaR)		<< "\n"
					<< radius*sin(nextThetaR)	<< " "	<< -top << " "	<< radius* cos(nextThetaR)	<< "\n";
		}

		myfile << "FIM";
		myfile.close();

	}
	
	return 0;
}

