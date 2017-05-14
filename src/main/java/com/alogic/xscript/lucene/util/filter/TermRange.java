package com.alogic.xscript.lucene.util.filter;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.BytesRef;

import com.alogic.xscript.lucene.util.FilterBuilder;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

/**
 * TermRangeQuery(字符串范围搜索)
 *
 */

public class TermRange extends FilterBuilder.Abstract {
	
	protected Query filter;
	
	protected String occur = "MUST";
	
	@Override
	public Query getFilter(Properties p) {
		return filter;
	}
	
	@Override
	public String getOccur() {
		return occur;
	}

	@Override
	public void configure(Properties p) {
		String field = PropertiesConstants.getString(p, "field", "content");
		String lowerTerm = PropertiesConstants.getString(p, "lowerTerm", null);
		String upperTerm = PropertiesConstants.getString(p, "upperTerm", null);
		Boolean includeLower = PropertiesConstants.getBoolean(p, "includeLower", true);
		Boolean includeUpper = PropertiesConstants.getBoolean(p, "includeUpper", true);
	    BytesRef lower = lowerTerm == null ? null : new BytesRef(lowerTerm);
	    BytesRef upper = upperTerm == null ? null : new BytesRef(upperTerm);
	    occur = PropertiesConstants.getString(p, "occur", "MUST");
	    filter = new TermRangeQuery(field, lower, upper, includeLower, includeUpper);
	}

}
