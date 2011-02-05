package liberus.tarot.os.widget;

import liberus.tarot.android.noads.TarotBotBeta;

import liberus.tarot.os.activity.TarotBotActivity;
import liberus.tarot.os.widget.TarotBotMediumWidget;
import liberus.tarot.os.widget.TarotBotWidget;
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
		return TarotBotBeta.class;
	}
}