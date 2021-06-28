package com.clb.pojo.arcgis;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArcgisResultItem {
	
	private ArcgisError error;
	
	private boolean success;
	
	private Long objectId;
	
	private String globalId;


	
	
}

