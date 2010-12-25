package liberus.tarot.os.widget;

import liberus.tarot.os.TarotBotOS;
import liberus.tarot.os.activity.CardForTheDayActivity;
import liberus.tarot.os.activity.TarotBotActivity;
import liberus.utils.WebUtils;


public class TarotBotMediumWidget extends TarotBotWidget
{    
	@Override
	public String getPath() {
		return WebUtils.md5("tarotbot")+"/"+WebUtils.md5("liberus.tarot.android");
	}
	@Override
	public Class getWidgetClass() {
		return TarotBotMediumWidget.class;
	}
	@Override
	public Class getActivityClass() {
		return TarotBotOS.class;
	}
}