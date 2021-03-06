attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord;

uniform mat4 u_projTrans;

//Output to Fragment Shader
varying vec4 vColor;
varying vec2 vTexCoord;

void main() {
    vColor = a_color;
    vTexCoord = a_texCoord;

    gl_Position = u_projTrans * a_position;
}