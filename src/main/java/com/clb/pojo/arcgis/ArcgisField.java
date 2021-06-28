package com.clb.pojo.arcgis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArcgisField {

	private String name;
	private String type;
	private String alias;
	
	private int length;


	
}
