package org.apache.core.e.e;

import org.apache.core.e.CE;
import org.apache.core.e.L;

import java.util.ArrayList;

public interface SendChatMessageListener extends L
{
	void sendChatMessage(SendChatMessageEvent event);

	class SendChatMessageEvent extends CE<SendChatMessageListener>
	{

		private String message;
		private boolean modified;

		public SendChatMessageEvent(String message)
		{
			this.message = message;
		}

		public String getMessage()
		{
			return message;
		}

		public void setMessage(String message)
		{
			this.message = message;
			modified = true;
		}

		public boolean isModified()
		{
			return modified;
		}

		@Override
		public void fire(ArrayList<SendChatMessageListener> listeners)
		{
			listeners.forEach(listener -> listener.sendChatMessage(this));
		}

		@Override
		public Class<SendChatMessageListener> getListenerType()
		{
			return SendChatMessageListener.class;
		}
	}
}
