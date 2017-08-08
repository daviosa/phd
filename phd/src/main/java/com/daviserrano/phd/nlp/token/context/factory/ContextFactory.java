package com.daviserrano.phd.nlp.token.context.factory;

import com.daviserrano.phd.nlp.token.Token;
import com.daviserrano.phd.nlp.token.context.Context;

public interface ContextFactory {

	public Context makeContext(Token token);

}
