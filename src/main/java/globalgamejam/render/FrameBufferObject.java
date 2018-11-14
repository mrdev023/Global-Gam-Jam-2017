package globalgamejam.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import globalgamejam.Main;
import globalgamejam.math.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by MrDev023 on 14/01/2017.
 */
public class FrameBufferObject {

    private int fbo,fboTexID,renderID,vbo,size;

    public FrameBufferObject(){
        int width = Main.WIDTH;
        int height = Main.HEIGHT;
        this.fbo = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);

        //Creation de la texture qui va contenir la sortie RGB du shader
        int renderedTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, renderedTexture);
        glTexImage2D(GL_TEXTURE_2D, 0,GL_RGB, width, height, 0,GL_RGB, GL_UNSIGNED_BYTE, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        //Creation du tampon de profondeur
        int depthrenderbuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, depthrenderbuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthrenderbuffer);

        //Definir le render Texture
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0,GL_TEXTURE_2D, renderedTexture, 0);
        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
            throw new IllegalStateException("FBO not loaded !");
        fboTexID = renderedTexture;
        renderID = depthrenderbuffer;


        this.vbo = GL15.glGenBuffers();
        float[] a = new float[]{
                0,0,        0.0f,0.0f,
                1,0,         1.0f,0.0f,
                1,1,          1.0f,1.0f,
                0,1,         0.0f,1.0f
        };
        FloatBuffer buffer = BufferUtils.createFloatBuffer(a.length);
        buffer.put(a).flip();
        size = a.length/(2+2);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        GL20.glEnableVertexAttribArray(Shaders.MAIN_SHADERS.getAttribLocation("vert"));
        GL20.glVertexAttribPointer(Shaders.MAIN_SHADERS.getAttribLocation("vert"), 2, GL11.GL_FLOAT, false, (2+2)*4, 0);

        GL20.glEnableVertexAttribArray(Shaders.MAIN_SHADERS.getAttribLocation("vertTexCoord"));
        GL20.glVertexAttribPointer(Shaders.MAIN_SHADERS.getAttribLocation("vertTexCoord"), 2, GL11.GL_FLOAT, true, (2+2)*4, 2*4);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void startRenderToFBO(){
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
        IntBuffer drawBuffs = BufferUtils.createIntBuffer(1);
        drawBuffs.put(0, GL_COLOR_ATTACHMENT0);
        GL20.glDrawBuffers(drawBuffs);
        glViewport(0,0,Main.WIDTH,Main.HEIGHT);
    }

    public void stopRenderToFBO(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void renderFBO(){

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0,0,Main.WIDTH,Main.HEIGHT);

        Shaders.MAIN_FBO.bind();
        Shaders.MAIN_FBO.uniform("projection", (new Matrix4f()).Ortho2D(0,1,0,1,-1,1).mul(new Matrix4f().translate(.5f,.5f,0)));
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, fboTexID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, size);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        glBindTexture(GL_TEXTURE_2D, 0);
        Shaders.MAIN_SHADERS.unbind();
    }

    public void destroy(){
        glDeleteTextures(fboTexID);
        glDeleteRenderbuffers(renderID);
        glDeleteFramebuffers(fbo);
    }

}
