#install.packages("rjson")
#install.packages("jsonlite")
library(GA)
library(jsonlite)

json <- fromJSON("../Desktop/cn3/data/json/o.json")

nr_clientes = json$meta_data$nb_customers
nr_depositos = json$meta_data$nb_depots
vehicle_cap = json$meta_data$vehicle_cap
vehicle_cost = json$meta_data$vehicle_cost
cost_type = json$meta_data$cost_type
clientes = json$customers
depositos = json$depots

customerDistances = matrix(nrow=nr_clientes, ncol = nr_clientes)
customerDemand = matrix(ncol=nr_clientes, nrow=1)
depositosDistances = matrix(nrow = nr_clientes, ncol = nr_depositos)
depositosStock = matrix(ncol=nr_depositos, nrow=1)
#calculate clientes distances from c0 -> c49

i = 1
for(depot in depositos){
  depositosStock[i] = depot$capacity
  i = i+1
}

i=1
j=1

for (c1 in clientes){
  x1 = c1$x;
  y1 = c1$y;
  customerDemand[i] = c1$demand;
  for (c2 in clientes){
    x2 = c2$x;
    y2 = c2$y;
    d = sqrt((x1 - x2)^2 + (y1-y2)^2);
    customerDistances[i,j] = d;
    customerDistances[j,i] = d;
    j = j+1;
  }
  j=1
  i = i+1;
}

i=1
j=1

for (c in clientes){
  x1 = c$x;
  y1 = c$y;
  for (d in depositos){
    x2 = d$x;
    y2 = d$y;
    distance = sqrt((x1 - x2)^2 + (y1-y2)^2);
    depositosDistances[i,j] = distance;
    j = j+1;
  }
  j=1
  i = i+1;
}

remove(i,j, x1, x2, y1, y2, distance, c, c1, c2, d, depot)
remove(clientes, depositos, json)
