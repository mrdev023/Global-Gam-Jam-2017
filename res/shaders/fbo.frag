#version 150
//Il n’y a pas de layout(location=i) dans OpenGL < 3.3, mais tu peux utiliser glFragData[i] = myvalue à la place.
uniform sampler2D materialTex;

in vec2 fragTexCoord;
in vec3 fragVert;

out vec4 finalColor;

void main() {
    finalColor = texture(materialTex, fragTexCoord);
}