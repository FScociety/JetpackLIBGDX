#ifdef GL_ES
    #define LOWP lowp
    precision LOWP float;
#else
    #define LOWP
#endif

//attributes from vertex shader
varying LOWP vec4 vColor;
varying vec2 vTexCoord;

//texture samplers
uniform sampler2D u_texture;
uniform sampler2D u_normals;

//values for shading algorithm
uniform vec2 Resolution;
uniform vec3 LightPos;
uniform LOWP vec4 LightColor;
uniform LOWP vec4 AmbientColor;
uniform vec3 Falloff;

void main() {

    //RGBA of diffuse color
    vec4 DiffuseColor = texture2D(u_texture, vTexCoord);

    //RGB of normal map
    vec3 NormalMap = texture2D(u_normals, vTexCoord).rgb;

    //delta pos of light
    vec3 LightDir = vec3(LightPos.xy - (gl_FragCoord.xy / Resolution.xy), LightPos.z);

    //Coorect for aspect ratio
    LightDir.x *= Resolution.x / Resolution.y;

    //Determine distance BEFORE we normalize Light Dir
    float D = length(LightDir);

    //Normalize verators
    vec3 N = normalize(NormalMap * 2.0 - 1.0);
    vec3 L = normalize(LightDir);

    //Pre-mul lgiht color with light intensit
    vec3 Diffuse = (LightColor.rgb * LightColor.a) * max(dot(N, L), 0.0);

    //Pre-mul Ambient wiht Color Intensity
    vec3 Ambient = AmbientColor.rgb * AmbientColor.a;

    //calculate attenuation
    float Attenuation = 1.0 / (Falloff.x + (Falloff.y * D) + (Falloff.z * D * D));

    vec3 Intensity = Ambient + Diffuse * Attenuation;
    vec3 FinalColor = DiffuseColor.rgb * Intensity;

    gl_FragColor = vColor * vec4(FinalColor, DiffuseColor.a);
}