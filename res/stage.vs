#version 330 core

layout(location = 0) in vec2 position;

void main()
{
    gl_Position = vec4(position.x/960.0f, position.y/540.0f, 0.0,  1.0);
}