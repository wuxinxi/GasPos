/**  
 * Project Name:Q6  
 * File Name:libtest.java  
 * Package Name:com.szxb.jni  
 * Date:Apr 13, 20177:37:45 PM  
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.  
 *  
 */

package com.szxb.jni;


import android.content.res.AssetManager;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * ClassName:libtest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Apr 13, 2017 7:37:45 PM <br/>
 * 
 * @author lilei
 * @version
 * @since JDK 1.6
 * @see
 */
public class libszxb {

	
	static {
		try {
			System.loadLibrary("ymodem");
		} catch (Throwable e) {
			Log.e("jni", "i can't find ymodem so!");
			e.printStackTrace();
		}
	}


	//更新固件
	public static native int ymodemUpdate(AssetManager ass, String filename);


}
