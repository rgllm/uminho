import React from 'react';

/*
* Fetch error helper
*
* @param {object} response
*/
export const handleResponse = (response) =>{
	return response.json().then(json => {
		return response.ok ? json : Promise.reject(json);
	});
}

export const renderChangePercent = (percent) =>{
	if(percent > 0){
		return <span className="percent-raised">{percent}% &uarr;</span>
	} else if(percent < 0 ){
		return <span className="percent-fallen">{percent}% &darr;</span>
	}else{
		return <span>{percent}%</span>
	}
}

export const renderProfit = (current, old, type) =>{
	current = parseFloat(current);
	old = parseFloat(old);

	if (type == "sell") {
		if (old > current)
			return <span className="percent-raised">{Math.round((old - current) * 100) / 100}$ &uarr;</span>
		else 
			return <span className="percent-fallen">{Math.round((old - current) * 100) / 100}$ &darr;</span>
	} else {
		if (current - old > 0)
			return <span className="percent-raised">{Math.round((current - old) * 100) / 100}$ &uarr;</span>
		else 
			return <span className="percent-fallen">{Math.round((current - old) * 100) /100 }$ &darr;</span>
	}
}

export const renderTypeBox = (type) => {
	if (type == "sell")
		return <span className="Box-type-sell">{type.toUpperCase()}</span>
	else 
		return <span className="Box-type-buy">{type.toUpperCase()}</span>
}