#include "stdafx.h"
#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#define M_PI       3.14159265358979323846   // pi
#define M_PI_2     1.57079632679489661923   // pi/2

using namespace std;

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

int main(int argc, char* argv[]) {
	if (argc < 2) {
		printf("precisa de argumentos!\n");
		return -1;
	}
	string filename = argv[argc - 1];
	string destDir = "../../xml_3d/";

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
		myfile.close();
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
		myfile.close();

	}
	if (strcmp(argv[1], "sphere") == 0) {    //  ESFERA
		if (argc != 6) {
			printf("sintaxe: gerador sphere <radius> <slices> <stacks> <fileName>\n");
			return -1;
		}
		myfile.open(destDir + filename);
		myfile << sphere(stof(argv[2]), stoi(argv[3]), stoi(argv[4]));
		myfile.close();
	}
	if (strcmp(argv[1], "cone") == 0) {   //  CONE
		if (argc != 7) {
			printf("sintaxe: gerador cone <bottomRadius> <height> <slices> <stacks> <fileName>\n");
			return -1;
		}
		myfile.open(destDir + filename);
		myfile << cone(stof(argv[2]), stof(argv[3]), stoi(argv[4]), stoi(argv[5]));
		myfile.close();
	}


	return 0;
}

