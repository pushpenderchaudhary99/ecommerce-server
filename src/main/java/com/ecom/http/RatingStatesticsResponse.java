package com.ecom.http;

import java.util.Map;

public class RatingStatesticsResponse {
	  private Map<String, Integer> starCounts;
	    private Map<String, Integer> starPercentages;

	    public Map<String, Integer> getStarCounts() {
	        return starCounts;
	    }

	    public void setStarCounts(Map<String, Integer> starCounts) {
	        this.starCounts = starCounts;
	    }

	    public Map<String, Integer> getStarPercentages() {
	        return starPercentages;
	    }

	    public void setStarPercentages(Map<String, Integer> starPercentages) {
	        this.starPercentages = starPercentages;
	    }

		public RatingStatesticsResponse(Map<String, Integer> starCounts, Map<String, Integer> starPercentages) {
			super();
			this.starCounts = starCounts;
			this.starPercentages = starPercentages;
		}

}
