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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import bitfire.utils.ShaderLoader;

public final class Simple extends Filter<Simple> {

    private float intensity, saturation;

    public enum Param implements Parameter {
        //TODO:
        // @formatter:off
        Texture("u_texture0", 0),
        SourceIntensity("intensity", 0),
        SourceSaturation("saturation", 0);
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

    public Simple () {
        super(ShaderLoader.fromFile("screenspace", "simple"));
        saturation = 1f;
        intensity = 1f;

        rebind();
    }

    public Simple setInput (FrameBuffer buffer1) {
        this.inputTexture = buffer1.getColorBufferTexture();
        return this;
    }

    public Simple setInput (Texture texture1) {
        this.inputTexture = texture1;
        return this;
    }

    public void setIntensity (float intensity) {
        this.intensity = intensity;
        setParam(Param.SourceIntensity, intensity);
    }

    public void setSaturation (float saturation) {
        this.saturation = saturation;
        setParam(Param.SourceSaturation, saturation);
    }

    public float getSource1Intensity () {
        return intensity;
    }

    public float getSource1Saturation () {
        return saturation;
    }


    @Override
    public void rebind () {
        setParams(Param.Texture, u_texture0);
        setParams(Param.SourceIntensity, intensity);
        setParams(Param.SourceSaturation, saturation);
        endParams();
    }

    @Override
    protected void onBeforeRender () {
        inputTexture.bind(u_texture0);
    }
}
