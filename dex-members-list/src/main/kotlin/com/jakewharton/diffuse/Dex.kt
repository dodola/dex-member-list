package com.jakewharton.diffuse

import com.jakewharton.dex.DexMember
import com.jakewharton.dex.MemberList
import com.jakewharton.dex.toMemberList
import okio.ByteString
import com.android.dex.Dex as AndroidDex

class Dex private constructor(private val bytes: ByteString) {
  private val dex: AndroidDex by lazy { AndroidDex(bytes.toByteArray()) }
  private val memberList: MemberList by lazy { dex.toMemberList() }

  val strings: List<String> get() = dex.strings()
  val types: List<String> get() = dex.typeNames()
  val classes: List<String> by lazy { dex.classDefs().map { dex.typeNames()[it.typeIndex] }}
  val allMembers: List<DexMember> get() = memberList.all
  val declaredMembers: List<DexMember> get() = memberList.declared
  val referencedMembers: List<DexMember> get() = memberList.referenced

  companion object {
    @JvmStatic
    @JvmName("create")
    fun ByteString.toDex() = Dex(this)
  }
}
