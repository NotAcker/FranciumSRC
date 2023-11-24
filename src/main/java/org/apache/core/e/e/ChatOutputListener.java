package org.apache.core.e.e;

import org.apache.core.e.CE;
import org.apache.core.e.L;

import java.util.ArrayList;

public interface ChatOutputListener extends L {
	void onSendMessage(ChatOutputEvent event);

	class ChatOutputEvent extends CE<ChatOutputListener>
	{

		private final String originalMessage;
		private String message;

		public ChatOutputEvent(String message)
		{
			originalMessage = this.message = message;
		}

		public String getOriginalMessage()
		{
			return originalMessage;
		}

		public String getMessage()
		{
			return message;
		}

		public void setMessage(String message)
		{
			this.message = message;
		}

		public boolean isModified()
		{
			return !originalMessage.equals(message);
		}

		@Override
		public void fire(ArrayList<ChatOutputListener> listeners)
		{
			for (ChatOutputListener listener : listeners)
			{
				listener.onSendMessage(this);
				if (isCancelled())
					return;
			}
		}

		@Override
		public Class<ChatOutputListener> getListenerType()
		{
			return ChatOutputListener.class;
		}
	}
}
