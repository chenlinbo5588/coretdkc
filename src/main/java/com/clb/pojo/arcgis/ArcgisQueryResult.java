package com.clb.pojo.arcgis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class ArcgisQueryResult<F> {

	//private String resp;
	
	private String objectIdFieldName;
	private String globalIdFieldName;
	private String geometryType;
	private SpatialReference spatialReference;
	
	private List<ArcgisField> fields;
	
	private ArcgisError error;
	
	private List<ArcgisEntity<F>> features;
	
	

	
}
