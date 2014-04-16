package org.macrogl



import scala.collection._



final class RenderBuffer(val format: Int, val width: Int, val height: Int)(implicit gl: Macrogl)
extends Handle {
  private var rbtoken = Token.RenderBuffer.invalid
  private val result = new Array[Int](1)

  def token = rbtoken

  def acquire() {
    release()
    rbtoken = gl.createRenderBuffer
    allocateStorage()
  }

  private def allocateStorage() {
    val oldbinding = gl.getCurrentRenderBufferBinding
    gl.bindRenderbuffer(Macrogl.RENDERBUFFER, this.token)
    gl.renderbufferStorage(Macrogl.RENDERBUFFER, format, width, height)
    gl.bindRenderbuffer(Macrogl.RENDERBUFFER, oldbinding)
  }

  def release() {
    if (!gl.validRenderBuffer(rbtoken)) {
      gl.deleteRenderBuffer(rbtoken)
      rbtoken = Token.RenderBuffer.invalid
    }
  }

}
