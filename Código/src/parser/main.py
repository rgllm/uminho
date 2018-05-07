#!/usr/bin/python
import argparse
from PsoTools import PsoTools
import sys

def main():
	
	# Get the input parameters
	parser = argparse.ArgumentParser()
	parser.add_argument('-i', type=str, dest='inputFile', 
						help='Input raw file')
	parser.add_argument('-o', type=str, dest='outputFile', 
						help='Output json file')
	
	if len(sys.argv) < 3 :
		parser.print_help()
		sys.exit(1)
		
	args = parser.parse_args()
	inputFile = args.inputFile
	outputFile = args.outputFile
		
	# Format the raw file to a json file
	tools = PsoTools()
	tools.rawToJson(inputFile, outputFile)
	#~ tools.plotCustomers(outputFile)
	tools.plotAll(outputFile)
	
if __name__ == '__main__':
	main()
