#version 150

uniform mat4 projection;
uniform mat4 camera;
uniform mat4 transform;

in vec3 vert;
in vec2 vertTexCoord;

out vec3 fragVert;
out vec2 fragTexCoord;

void main() {
    // Pass some variables to the fragment shader
    fragTexCoord = vertTexCoord;
    fragVert = vert;

    // Apply all matrix transformations to vert
    gl_Position = projection * camera * transform * vec4(vert, 1);
}