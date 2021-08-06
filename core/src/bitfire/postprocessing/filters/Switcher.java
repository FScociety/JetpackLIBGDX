/*******************************************************************************
 * Copyright 2012 bmanuel
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package bitfire.postprocessing.filters;

import bitfire.utils.ShaderLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public final class Switcher extends Filter<Switcher> {

	private float s1i, s1s, s2i, s2s, switcher;

	public enum Param implements Parameter {
		// @formatter:off
		Texture0("u_texture0", 0),
		Texture1("u_texture1", 0),
		Source1Intensity("Src1Intensity", 0),
		Source1Saturation("Src1Saturation", 0),
		Source2Intensity("Src2Intensity", 0),
		Source2Saturation("Src2Saturation", 0),
		Switch("Switch", 0);
		// @formatter:on

		private final String mnemonic;
		private int elementSize;

		private Param (String m, int elementSize) {
			this.mnemonic = m;
			this.elementSize = elementSize;
		}

		@Override
		public String mnemonic () {
			return this.mnemonic;
		}

		@Override
		public int arrayElementSize () {
			return this.elementSize;
		}
	}

	private Texture inputTexture2 = null;

	public Switcher() {
		super(ShaderLoader.fromFile("screenspace", "switch"));
		s1i = 1f;
		s2i = 1f;
		s1s = 1f;
		s2s = 1f;

		rebind();
	}

	public Switcher setInput (FrameBuffer buffer1, FrameBuffer buffer2) {
		this.inputTexture = buffer1.getColorBufferTexture();
		this.inputTexture2 = buffer2.getColorBufferTexture();
		return this;
	}

	public Switcher setInput (Texture texture1, Texture texture2) {
		this.inputTexture = texture1;
		this.inputTexture2 = texture2;
		return this;
	}

	public void setSource1Intensity (float intensity) {
		s1i = intensity;
		setParam(Switcher.Param.Source1Intensity, intensity);
	}

	public void setSource2Intensity (float intensity) {
		s2i = intensity;
		setParam(Switcher.Param.Source2Intensity, intensity);
	}

	public void setSource1Saturation (float saturation) {
		s1s = saturation;
		setParam(Switcher.Param.Source1Saturation, saturation);
	}

	public void setSource2Saturation (float saturation) {
		s2s = saturation;
		setParam(Switcher.Param.Source2Saturation, saturation);
	}

	public void setSwitch(float switcher) {
		this.switcher = switcher;
		setParam(Param.Switch, switcher);
	}

	public float getSource1Intensity () {
		return s1i;
	}

	public float getSource2Intensity () {
		return s2i;
	}

	public float getSource1Saturation () {
		return s1s;
	}

	public float getSource2Saturation () {
		return s2s;
	}

	@Override
	public void rebind () {
		setParams(Param.Texture0, u_texture0);
		setParams(Param.Texture1, u_texture1);
		setParams(Param.Source1Intensity, s1i);
		setParams(Param.Source2Intensity, s2i);
		setParams(Param.Source1Saturation, s1s);
		setParams(Param.Source2Saturation, s2s);
		setParams(Param.Switch, this.switcher);
		endParams();
	}

	@Override
	protected void onBeforeRender () {
		inputTexture.bind(u_texture0);
		inputTexture2.bind(u_texture1);
	}
}
