#install.packages("rjson")
library(GA)
library(jsonlite)

json <- fromJSON("~/desktop/cn3/data/json/o.json")

customers = json$meta_data$nb_customers
depots = json$meta_data$nb_depots
vehicle_cap = json$meta_data$vehicle_cap
vehicle_cost = json$meta_data$vehicle_cost
cost_type = json$meta_data$cost_type

#calculate customers distances from c0 -> c49

#calculate depots distances from d0 -> d4