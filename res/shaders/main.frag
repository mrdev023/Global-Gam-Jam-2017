#version 150

uniform sampler2D materialTex;
uniform vec4 color;

in vec2 fragTexCoord;
in vec3 fragVert;

out vec4 finalColor;

void main() {
    finalColor = texture(materialTex, fragTexCoord) * color;
}