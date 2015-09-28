package com.iflytek;

import java.util.Map;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.ViewInject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.speech.util.FucUtil;
import com.iflytek.speech.util.JsonParser;
import com.iflytek.speech.util.XmlParser;
import com.wujia.witstore.data.model.ResultInfo;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppString;

public class VoiceContorlImp {
	// 语音识别对象
	private SpeechRecognizer mAsr;
	// 语音合成对象
	private SpeechSynthesizer mTts;
	// 返回结果格式，支持：xml,json
	private String mResultType = "json";
	// 语音听写UI
	private RecognizerDialog iatDialog;
	private final String KEY_GRAMMAR_ABNF_ID = "grammar_abnf_id";
	int ret = 0;// 函数调用返回值
	private String mEngineType = "cloud";
	private Toast mToast;
	// 缓存
	private SharedPreferences mSharedPreferences;
	// 云端语法文件
	private String mCloudGrammar = null;
	private final String GRAMMAR_TYPE_ABNF = "abnf";

	private Context context;

	@SuppressLint("ShowToast")
	public void setRootView(Context context) {
		this.context= context;
		iatDialog = new RecognizerDialog(context, null);
		mSharedPreferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);

		mAsr = SpeechRecognizer.createRecognizer(context, null);
		mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		Editor editor = mSharedPreferences.edit();
		editor.putString(KEY_GRAMMAR_ABNF_ID, null);
		editor.commit();
		mCloudGrammar = FucUtil.readFile(context, "grammar_sample.abnf", "utf-8");

		// 初始合成对象
		hcInit();
		// 构建语法
		grammarInit();

	}

	/**
	 * 根据语音识别的命令进行解析成对应的参数以及命令
	 * */
	private void voiceHandle(String text) {

		if (text != null) {
			String mack = "";
			int id = 0;
			if (text.lastIndexOf("密集架") >= 0) {

				if (text.trim().startsWith("1")||text.trim().startsWith("第1")) {
					id = 7;

				} else if (text.trim().startsWith("2")||text.trim().startsWith("第2")) {
					id = 8;

				} else if (text.trim().startsWith("3")||text.trim().startsWith("第3")) {
					id = 9;

				} else if (text.trim().startsWith("4")||text.trim().startsWith("第4")) {
					id = 20;

				} else if (text.trim().startsWith("5")||text.trim().startsWith("第5")) {
					id = 21;

				} else if (text.lastIndexOf("通风") > 0) {
					id = 7;

				} else if (text.lastIndexOf("合拢") > 0) {
					id = 7;

				}else if (text.lastIndexOf("停止") > 0) {
					id = 7;

				}

				if (id > 0) {
					mack=setHandleParm(text, mack);
					VoiceContorl.imshandle(mack, id, callback_handles);
					return;
				}

			} else if (text.lastIndexOf("灯") >= 0) {

				if (text.lastIndexOf("库房灯") == 0) {

					id = 1;
				} else if (text.lastIndexOf("灯一") == 0) {

					id = 2;
				} else if (text.lastIndexOf("灯二") == 0) {
					id = 3;
				}
				if (id > 0) {
					mack=setHandleParm(text, mack);
					VoiceContorl.ilshandle(id, Integer.parseInt(mack),
							callback_handles);
					return;
				}

			} else if (text.lastIndexOf("门") > 0) {
				if (text.lastIndexOf("玻璃门") == 0) {
					id = 1;
				} else if (text.lastIndexOf("木门") == 0) {
					id = 2;
				} else if (text.lastIndexOf("库房门") == 0) {
					id = 3;
				} else if (text.lastIndexOf("前台门") == 0) {
					id = 4;
				}

				if (id > 0) {
					mack=setHandleParm(text, mack);
					VoiceContorl.acshandle(id, Integer.parseInt(mack),
							callback_handles);
					return;
				}
			}
			String ret = "我不能执行这个命令！";
			mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
			mTts.startSpeaking(ret, mTtsListener);
		}

	}

	public void hcInit() {
		// 初始合成对象
		if (mTts == null) {
			mTts = SpeechSynthesizer.createSynthesizer(context, null);
			mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoli");
			mTts.setParameter(SpeechConstant.SPEED, "50");
			// 设置语速
			mTts.setParameter(SpeechConstant.VOLUME, "80");
			// 设置音量，范围 0~100
			mTts.setParameter(SpeechConstant.ENGINE_TYPE,
					SpeechConstant.TYPE_CLOUD);
			// 设置云端 //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm” //保存在 SD
			// 卡需要在
			// AndroidManifest.xml 添加写 SD 卡权限 //仅支持保存为 pcm格式，如果不需要保存合成音频，注释该行代码
			mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,"./sdcard/iflytek.pcm");
		}
	}

	String mContent;// 语法、词典临时变量

	private void grammarInit() {
		String grammarId = mSharedPreferences.getString(KEY_GRAMMAR_ABNF_ID,
				null);
		if (TextUtils.isEmpty(grammarId)) {
			mContent = new String(mCloudGrammar);
			// 指定引擎类型
			mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
			// 设置文本编码格式
			mAsr.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
			// showText.setText(mContent);
			ret = mAsr.buildGrammar(GRAMMAR_TYPE_ABNF, mContent,
					grammarListener);
			if (ret != ErrorCode.SUCCESS)
				showTip("语法构建失败,错误码：" + ret);
		}
	}

	/**
	 * 构建语法监听器。
	 */
	private GrammarListener grammarListener = new GrammarListener() {
		@Override
		public void onBuildFinish(String grammarId, SpeechError error) {
			if (error == null) {
				if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
					Editor editor = mSharedPreferences.edit();
					if (!TextUtils.isEmpty(grammarId))
						editor.putString(KEY_GRAMMAR_ABNF_ID, grammarId);
					editor.commit();
				}
				// showTip("语法构建成功：" + grammarId);
			} else {
				// showTip("语法构建失败,错误码：" + error.getErrorCode());
			}
		}
	};

	/**
	 * 听写UI监听器
	 */
	private RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {
		public void onResult(RecognizerResult result, boolean isLast) {
			if (null != result && !TextUtils.isEmpty(result.getResultString())) {
				//	Log.d(TAG, "recognizer result：" + result.getResultString());
				String text = "";
				if (mResultType.equals("json")) {
					text = JsonParser.parseIatResult(result.getResultString());
				} else if (mResultType.equals("xml")) {
					text = XmlParser.parseNluResult(result.getResultString());
				}
				// 显示
				if (text==null ) {
					String ret = "无法识别命令";
					mTts.startSpeaking(ret, mTtsListener);

					return;
				}

				mTts.startSpeaking(text+"正在执行中...", mTtsListener);
				voiceHandle(text);

			} else {
				//	Log.d(TAG, "recognizer result : null");
			}
		}

		/**
		 * 识别回调错误.
		 */
		public void onError(SpeechError error) {
			showTip(error.getPlainDescription(true));
		}

	};

	private String setHandleParm(String text, String mack) {
		if (text.lastIndexOf("左开") > 0) {
			mack = "1";

		}else
		if (text.lastIndexOf("右开") > 0) {
			mack = "2";

		}else
		if (text.lastIndexOf("停止") > 0) {
			mack = "0";
		}else
		if (text.lastIndexOf("通风") > 0) {
			mack = "3";
		}else
		if (text.lastIndexOf("合拢") > 0) {
			mack = "4";
		}else
		if (text.lastIndexOf("打开") > 0||text.lastIndexOf("开")> 0) {
			mack = "1";
		}else
		if (text.lastIndexOf("关闭") > 0||text.lastIndexOf("关")> 0) {
			mack = "0";
		}
		return mack;
	}

	/**
	 * 参数设置
	 *
	 * @return
	 */
	public boolean setParam() {
		boolean result = false;
		// 清空参数
		mAsr.setParameter(SpeechConstant.PARAMS, null);
		// 设置识别引擎
		mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
		if ("cloud".equalsIgnoreCase(mEngineType)) {
			String grammarId = mSharedPreferences.getString(
					KEY_GRAMMAR_ABNF_ID, null);
			if (TextUtils.isEmpty(grammarId)) {
				result = false;
			} else {
				// 设置返回结果格式
				mAsr.setParameter(SpeechConstant.RESULT_TYPE, mResultType);
				// 设置云端识别使用的语法id
				mAsr.setParameter(SpeechConstant.CLOUD_GRAMMAR, grammarId);
				result = true;
			}
		}
		return result;
	}

	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {
		@Override
		public void onSpeakBegin() {
			showTip("开始播放");
		}

		@Override
		public void onSpeakPaused() {
			showTip("暂停播放");
		}

		@Override
		public void onSpeakResumed() {
			showTip("继续播放");
		}

		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos,
									 String info) {
//			// 合成进度
//			mPercentForBuffering = percent;
//			showTip(String.format(getString(R.string.tts_toast_format),
//					mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
//			// 播放进度
//			mPercentForPlaying = percent;
//			showTip(String.format(getString(R.string.tts_toast_format),
//					mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onCompleted(SpeechError error) {
			if (error == null) {
				showTip("播放完成");
			} else if (error != null) {
				showTip(error.getPlainDescription(true));
			}
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

		}
	};

	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}

	public HttpCallBack callback_handles = new HttpCallBack() {
		@Override
		public void onSuccess(Map<String, String> headers, byte[] t) {
			try {
				super.onSuccess(headers, t);
				String result = new String(t);
				Log.e("callback_handles", result);
				ResultInfo resultInfo = MyHttpUtil.getInstance().getResultInfo(
						result);
				if (resultInfo.getStatus().equals("0")) {

					if (resultInfo.getFlag().equals("IMS_RIGHTOPEN")) {

						mTts.startSpeaking("密集架右开成功", mTtsListener);
					}
					if (resultInfo.getFlag().equals("IMS_LEFTOPEN")) {

						mTts.startSpeaking("密集架左开成功", mTtsListener);
					}
					if (resultInfo.getFlag().equals("IMS_WIND")) {

						mTts.startSpeaking("密集架通风成功", mTtsListener);
					}
					if (resultInfo.getFlag().equals("IMS_CLOSE")) {

						mTts.startSpeaking("密集架合拢成功", mTtsListener);
					}
					if (resultInfo.getFlag().equals("ILS_OPEN")) {
						mTts.startSpeaking("灯打开成功", mTtsListener);
					}
					if (resultInfo.getFlag().equals("ILS_CLOSE")) {
						mTts.startSpeaking("灯关闭成功", mTtsListener);
					}
					if (resultInfo.getFlag().equals("ACS_OPEN")) {
						mTts.startSpeaking("门打开成功", mTtsListener);
					}
					if (resultInfo.getFlag().equals("ACS_CLOSE")) {
						mTts.startSpeaking("门关闭成功", mTtsListener);
					}



				} else {

					mTts.startSpeaking("操作失败", mTtsListener);
				}
			} catch (Exception e) {


				mTts.startSpeaking("操作失败！", mTtsListener);
			} finally {

			}
		}

		@Override
		public void onFailure(int errorNo, String strMsg) {
			super.onFailure(errorNo, strMsg);

			mTts.startSpeaking("操作失败", mTtsListener);
			ViewInject.toast(AppString.ERR_NKFT + strMsg);
		}
	};

	public void startVoice(){
		// 显示听写对话框
		setParam();
		iatDialog.setListener(recognizerDialogListener);
		iatDialog.show();
	}

	public void close(){
		mAsr.cancel();
		mAsr.destroy();
	}

}
