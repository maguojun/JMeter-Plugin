package com.upsmart.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class Generate_trans_data extends AbstractJavaSamplerClient {

	private static String label = "Generate transaction data";
	private long pri_acct_no;
	private String mchnt_cd;
	private String term_id;
	private int amount;
	private String generate_date;
	private String file_path;
	private int valid_count;
	private int invaild_count;
	FileOutputStream _fos = null;
	OutputStreamWriter _osw = null;
	File file = null;

	
	public void setupTest(JavaSamplerContext context) {

		file_path = context.getParameter("file_path");
		generate_date = context.getParameter("generate_date");

		// Calendar calendar = Calendar.getInstance();
		// int year = calendar.get(Calendar.YEAR);
		// int month = calendar.get(Calendar.MONTH);
		// int day =calendar.get(Calendar.DAY_OF_MONTH);
		StringBuilder _builder = new StringBuilder();
		_builder.append(generate_date);
		_builder.append(".txt");

		file = new File(file_path, _builder.toString());

		if (!file.exists()) {
			try {

				file.createNewFile();

			} catch (IOException e) {

				e.printStackTrace();

			}
		}

		try {

			_fos = new FileOutputStream(file, true);
			_osw = new OutputStreamWriter(_fos, "utf-8");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void teardownTest(JavaSamplerContext arg0) {

		try {
			_osw.close();
			_fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Arguments getDefaultParameters() {

		Arguments params = new Arguments();
		params.addArgument("pri_acct_no", "card_no");
		params.addArgument("mchnt_cd", "merch_mid");
		params.addArgument("term_id", "pos_id");
		params.addArgument("amount", "1000");
		params.addArgument("file_path", "/home/upsmart/");
		params.addArgument("generate_date", "2013-09-25");
		params.addArgument("valid_count", "1");
		params.addArgument("invaild_count", "0");

		return params;
	}

	@Override
	public SampleResult runTest(JavaSamplerContext context) {

		String trans_data = "16rep_card,rep_mid,S22,5416,115810,1020005,0,1,01,1,03,04,0,0,14,3,rep_term,rep_amount,rep_date 23:59:59.304959";

		pri_acct_no = context.getLongParameter("pri_acct_no");
		mchnt_cd = context.getParameter("mchnt_cd");
		term_id = context.getParameter("term_id");
		amount = context.getIntParameter("amount");
		valid_count = context.getIntParameter("valid_count");
		invaild_count = context.getIntParameter("invaild_count");

		try {

			for (int i = 0; i < valid_count; i++) {
				trans_data = trans_data.replace("rep_card", pri_acct_no + "")
						.replace("rep_mid", mchnt_cd)
						.replace("rep_term", term_id)
						.replace("rep_amount", amount + "")
						.replace("rep_date", generate_date);
				_osw.write(trans_data);
				_osw.write("\r\n");
				pri_acct_no = (long) pri_acct_no + 1;
				amount = amount + 1;
			}

			_osw.flush();

			pri_acct_no = 8888888888888888l;

			for (int i = 0; i < invaild_count; i++) {

				trans_data = trans_data.replace("rep_card", pri_acct_no + "")
						.replace("rep_mid", mchnt_cd)
						.replace("rep_term", term_id)
						.replace("rep_amount", amount + "");

				_osw.write(trans_data);
				_osw.write("\r\n");
			}

			_osw.flush();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SampleResult sr;
		sr = new SampleResult();
		sr.setSampleLabel(label);
		try {
			sr.sampleStart(); // 记录程序执行时间，以及执行结果
			sr.setSuccessful(true);

		} catch (Throwable e) {
			sr.setSuccessful(false);
		} finally {
			sr.sampleEnd();
		}
		return sr;
	}

}
