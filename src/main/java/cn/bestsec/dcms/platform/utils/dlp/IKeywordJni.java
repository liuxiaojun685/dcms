package cn.bestsec.dcms.platform.utils.dlp;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;

public interface IKeywordJni extends Library {
    IKeywordJni instance = (IKeywordJni) Native.loadLibrary("TaskMgr", IKeywordJni.class);

	public void Test();

	public int StartTaskToMgr(WString taskId, WString inPath, WString outPath);

	public int FindTaskStatusFromMgr(WString taskId, Pointer result);

	public int ReleaseTaskFromList(WString taskId);
}
