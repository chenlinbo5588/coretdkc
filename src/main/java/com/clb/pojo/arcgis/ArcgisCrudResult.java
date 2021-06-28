package com.clb.pojo.arcgis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ArcgisCrudResult {

	private List<ArcgisResultItem> addResults;
	private List<ArcgisResultItem> deleteResults;
	private List<ArcgisResultItem> updateResults;
	
	private ArcgisError error;
	

	
}
