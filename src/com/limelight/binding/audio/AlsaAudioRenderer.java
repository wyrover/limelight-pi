package com.limelight.binding.audio;

import com.limelight.LimeLog;
import com.limelight.nvstream.av.audio.AudioRenderer;

/**
 * Audio renderer implementation
 * @author Iwan Timmer
 */
public class AlsaAudioRenderer implements AudioRenderer {
	
	private String device;
	
	public AlsaAudioRenderer(String device) {
		this.device = device;
	}

	@Override
	public boolean streamInitialized(int channelCount, int sampleRate) {
		return AlsaAudio.init(channelCount, sampleRate, device) == 0;
	}

	@Override
	public void playDecodedAudio(byte[] bytes, int offset, int length) {
		int rc = AlsaAudio.play(bytes, offset, length);
		
		if (rc<0)
			LimeLog.warning("Alsa error from writei: "+rc);
		else if (rc!=length/4)
			LimeLog.warning("Alsa short write, write "+rc+" frames");
	}

	@Override
	public void streamClosing() {
		AlsaAudio.close();
	}

	@Override
	public int getCapabilities() {
		return 0;
	}
	
}
