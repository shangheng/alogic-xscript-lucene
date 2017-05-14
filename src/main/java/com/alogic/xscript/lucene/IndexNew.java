package com.alogic.xscript.lucene;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.index.IndexWriter;

import com.alogic.xscript.ExecuteWatcher;
import com.alogic.xscript.Logiclet;
import com.alogic.xscript.LogicletContext;
import com.alogic.xscript.lucene.util.BlobIndexBuilder;
import com.alogic.xscript.lucene.util.TextIndexBuilder;
import com.anysoft.util.BaseException;
import com.anysoft.util.Properties;

public class IndexNew extends IndexWriterOperation {
	
	protected String type = "";
	protected String id = "";
	protected String value = "";

	public IndexNew(String tag, Logiclet p) {
		super(tag, p);
	}

	@Override
	public void configure(Properties p) {
		super.configure(p);

		type = p.GetValue("type", type, false, true);
		id = p.GetValue("id", id, false, true);
		value = p.GetValue("value", value, false, true);
	}
	
	@Override
	protected void onExecute(IndexWriter indexWriter, Map<String, Object> root, Map<String, Object> current,
			LogicletContext ctx, ExecuteWatcher watcher) {	
		if (StringUtils.isEmpty(type)) {
			throw new BaseException("core.no_type", "type is not set,check your script.");
		}
		if (StringUtils.isEmpty(id)) {
			throw new BaseException("core.no_id", "id is not set,check your script.");
		}
		if (StringUtils.isEmpty(value)) {
			throw new BaseException("core.no_value", "value is not set,check your script.");
		}
		String ty = ctx.transform(type);
		String field = ctx.transform(id);
		String fieldValue = ctx.transform(value);
		if(ty.equals("text")) {
			new TextIndexBuilder(field, fieldValue).addDocument(indexWriter);
		} else if(ty.equals("Blob")) {
			new BlobIndexBuilder(field, fieldValue).addDocument(indexWriter);
		}	
	}

}
