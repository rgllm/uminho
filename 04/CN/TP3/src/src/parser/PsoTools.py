import itertools
import json
import matplotlib.pyplot as plt
from matplotlib import style
import os
style.use('ggplot')
import numpy as np
from pprint import pprint
from os.path import basename

xrange=range

class PsoTools(object):
	def __init__(self):
		pass
	
	
	# Convert a data raw file to a json file
	def rawToJson(self, inputFilePath, outputFilePath): 
		
		inFile = open(inputFilePath, mode='r')
		outFile = open(outputFilePath, mode='w')
		meta_data = dict.fromkeys(['nb_customers', 'nb_depots',
		'vehicle_cap', 'vehicle_cost', 'cost_type'])
		cust_dict = dict.fromkeys(['x', 'y', 'demand'])
		dep_dict = dict.fromkeys(['x', 'y', 'capacity'])
		customers = {}
		depots = {}
		
		# Number of customers and available depots
		nb_customers = int(inFile.readline())
		nb_depots = int(inFile.readline())
		meta_data['nb_customers'] = nb_customers
		meta_data['nb_depots'] = nb_depots
		inFile.readline()	# Empty line
		
		# Depots cordinates
		for i, line in enumerate(inFile):
			if i < nb_depots:
				x = float(line.split()[0])
				y = float(line.split()[1])
				depots['d'+str(i)] = {}
				depots['d'+str(i)]['x'] = x
				depots['d'+str(i)]['y'] = y
			else:
				i=i-1
				break
		
		# Customers cordinates and vehicule capacity		
		for i, line in enumerate(inFile):
			if i < nb_customers:
				x = float(line.split()[0])
				y = float(line.split()[1])
				customers['c'+str(i)] = {}
				customers['c'+str(i)]['x'] = x
				customers['c'+str(i)]['y'] = y
			else:
				break
		
		# Vehicules and depots capacity
		for i, line in enumerate(inFile):
			if i == 0:
				vehicle_cap = float(line)
				meta_data['vehicle_cap'] = vehicle_cap
			elif i == 1:
				pass
			elif i < nb_depots+2:
				depot_cap = float(line)
				depots['d'+str(i-2)]['capacity'] = depot_cap
			else:
				break
		
		# Customers demands		
		for i, line in enumerate(inFile):
			if i < nb_customers:
				demand = float(line)
				customers['c'+str(i)]['demand'] = demand
			else:
				break
		
		# Depots openning costs		
		for i, line in enumerate(inFile):
			if i < nb_depots:
				openning_cost = float(line)
				depots['d'+str(i)]['opening_cost'] = openning_cost
			elif i == nb_depots:
				pass
			elif i == nb_depots+1:	
				vehicle_cost = float(line)
				meta_data['vehicle_cost'] = vehicle_cost
			elif i == nb_depots+2:	
				pass
			elif i == nb_depots+3:	
				cost_type = float(line)
				meta_data['cost_type'] = cost_type
			else:
				break
				
		final_output = {}
		final_output['customers'] = customers
		final_output['depots'] = depots
		final_output['meta_data'] = meta_data
		
		json.dump(final_output, outFile, indent=4)	
		inFile.close()
		outFile.close()
	
	# Plot the customers on the map
	def plotCustomers(self, jsonInputFile):
		
		if os.path.isfile(jsonInputFile):
			
			with open(jsonInputFile) as data_file:
				data = json.load(data_file)
			
			nb_customers = data['meta_data']['nb_customers']
			coords_cust = np.zeros(shape=(nb_customers,2))
			
			for i in xrange(nb_customers):
				x = data['customers']['c{0}'.format(i)]['x']
				y = data['customers']['c{0}'.format(i)]['y']
				coords_cust[i] = [x,y]
			
			plt.scatter(coords_cust[:,0], coords_cust[:,1], marker='P', s=10, linewidth=5)
			plt.show()
			
			
	# Plot the depots on the map
	def plotDepots(self, jsonInputFile):
		
		if os.path.isfile(jsonInputFile):
			
			with open(jsonInputFile) as data_file:
				data = json.load(data_file)
			
			nb_depots = data['meta_data']['nb_depots']
			coords_depot = np.zeros(shape=(nb_depots,2))
			
			for i in xrange(nb_depots):
				x = data['depots']['d{0}'.format(i)]['x']
				y = data['depots']['d{0}'.format(i)]['y']
				coords_depot[i] = [x,y]
			
			plt.scatter(coords_depot[:,0], coords_depot[:,1], marker='P', s=10, linewidth=5)
			plt.show()
			
			
	# Plot both depots and customers on the map
	def plotAll(self, jsonInputFile):
		
		if os.path.isfile(jsonInputFile):
			
			with open(jsonInputFile) as data_file:
				data = json.load(data_file)
			
			nb_customers = data['meta_data']['nb_customers']
			nb_depots = data['meta_data']['nb_depots']
			coords_cust = np.zeros(shape=(nb_customers,2))
			coords_depot = np.zeros(shape=(nb_depots,2))
			
			for i in xrange(nb_customers):
				x = data['customers']['c{0}'.format(i)]['x']
				y = data['customers']['c{0}'.format(i)]['y']
				coords_cust[i] = [x,y]
				
			for i in xrange(nb_depots):
				x = data['depots']['d{0}'.format(i)]['x']
				y = data['depots']['d{0}'.format(i)]['y']
				coords_depot[i] = [x,y]


			filename = str(basename(os.path.splitext(jsonInputFile)[0]) + '.pdf')
			
			plt.scatter(coords_cust[:,0], coords_cust[:,1], marker='s', s=10, linewidth=5)
			plt.scatter(coords_depot[:,0], coords_depot[:,1], marker='8', s=10, linewidth=5)
			plt.savefig(filename, format='pdf')
			#~ plt.show()

