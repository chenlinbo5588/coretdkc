package com.clb.pojo.arcgis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArcgisEntity<Q> {

	private Geometry geometry;
	private Q attributes;

	
	
}
