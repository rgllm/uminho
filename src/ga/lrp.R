#install.packages("rjson")
#install.packages("jsonlite")
library(GA)
library(jsonlite)

json <- fromJSON("../Desktop/cn3/data/json/o.json")

n_customers = json$meta_data$nb_customers
n_depots = json$meta_data$nb_depots
vehicle_cap = json$meta_data$vehicle_cap
vehicle_cost = json$meta_data$vehicle_cost
cost_type = json$meta_data$cost_type
customers = json$customers
depots = json$depots

customerDistances = matrix(nrow=n_customers, ncol = n_customers)
customerDemand = matrix(ncol=n_customers, nrow=1)
depotsDistances = matrix(nrow = n_customers, ncol = n_depots)

#calculate customers distances from c0 -> c49

i=1
j=1

for (c1 in customers){
  x1 = c1$x;
  y1 = c1$y;
  customerDemand[i] = c1$demand;
  for (c2 in customers){
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

for (c in customers){
  x1 = c$x;
  y1 = c$y;
  for (d in depots){
    x2 = d$x;
    y2 = d$y;
    distance = sqrt((x1 - x2)^2 + (y1-y2)^2);
    depotsDistances[i,j] = distance;
    j = j+1;
  }
  j=1
  i = i+1;
}


