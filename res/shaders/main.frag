#version 150
//Il n’y a pas de layout(location=i) dans OpenGL < 3.3, mais tu peux utiliser glFragData[i] = myvalue à la place.
uniform sampler2D materialTex;
uniform vec4 color;

in vec2 fragTexCoord;
in vec3 fragVert;

//layout(location = 0) out vec4 finalColor;
// https://learnopengl.com/#!Lighting/Multiple-lights pour le lighing en cas de besoin

void main() {
    gl_FragData[0] = texture(materialTex, fragTexCoord) * color;
}