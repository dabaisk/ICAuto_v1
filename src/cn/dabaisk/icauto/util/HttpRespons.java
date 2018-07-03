package cn.dabaisk.icauto.util;

import java.util.Vector;

public class HttpRespons {

	public Vector<String> contentCollection;
	public String urlString;
	public int defaultPort;
	public String file;
	public String host;
	public String path;
	public int port;
	public int readTimeout;
	public int connectTimeout;
	public String contentType;
	public String method;
	public int code;
	public String contentEncoding;
	public String ref;
	public String query;
	public String protocol;
	public String userInfo;
	public String content;
	public String message;

	@Override
	public String toString() {
		return "HttpRespons [contentCollection=" + contentCollection + ", urlString=" + urlString + ", defaultPort="
				+ defaultPort + ", file=" + file + ", host=" + host + ", path=" + path + ", port=" + port
				+ ", readTimeout=" + readTimeout + ", connectTimeout=" + connectTimeout + ", contentType=" + contentType
				+ ", method=" + method + ", code=" + code + ", contentEncoding=" + contentEncoding + ", ref=" + ref
				+ ", query=" + query + ", protocol=" + protocol + ", userInfo=" + userInfo + ", content=" + content
				+ ", message=" + message + "]";
	}

}
