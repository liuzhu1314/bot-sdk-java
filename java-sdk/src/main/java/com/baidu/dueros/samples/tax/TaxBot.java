/** 
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baidu.dueros.samples.tax;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import com.baidu.dueros.bot.Base;
import com.baidu.dueros.data.request.IntentRequest;
import com.baidu.dueros.data.request.LaunchRequest;
import com.baidu.dueros.data.request.SessionEndedRequest;
import com.baidu.dueros.data.response.OutputSpeech;
import com.baidu.dueros.data.response.OutputSpeech.Type;
import com.baidu.dueros.data.response.Reprompt;
import com.baidu.dueros.data.response.Resource;
import com.baidu.dueros.data.response.card.TextCard;
import com.baidu.dueros.model.Response;

/**
 * 查询个税Bot例子，继承{@code Base}类
 * 
 * @author tianlonglong(tianlong02@baidu.com)
 * @version V1.0
 * @since 2017年10月5日
 */
public class TaxBot extends Base {

    /**
     * 重写Base构造方法
     * 
     * @param request
     *            servlet Request作为参数
     * @throws IOException
     *             抛出异常
     */
    public TaxBot(HttpServletRequest request) throws IOException {
        super(request);
    }

    /**
     * 重写onLaunch方法，处理onLaunch对话事件
     * 
     * @param launchRequest
     *            LaunchRequest请求体
     * @see com.baidu.dueros.bot.Base#onLaunch(com.baidu.dueros.data.request.LaunchRequest)
     */
    @Override
    protected Response onLaunch(LaunchRequest launchRequest) {
        /** 新建文本卡片 */
        TextCard textCard = new TextCard("所得税为您服务");
        /** 设置链接地址 */
        textCard.setUrl("www:....");
        /** 设置链接内容 */
        textCard.setAnchorText("setAnchorText");
        /** 添加引导话术 */
        textCard.addCueWord("欢迎进入");

        /** 新建返回的语音内容 */
        OutputSpeech outputSpeech = new OutputSpeech(Type.SSML, "所得税为您服务");

        /** 构造返回的Response */
        Response response = new Response(outputSpeech, textCard);
        return response;
    }

    /**
     * 重写onInent方法，处理onInent对话事件
     * 
     * @param intentRequest
     *            IntentRequest请求体
     * @see com.baidu.dueros.bot.Base#onInent(com.baidu.dueros.data.request.IntentRequest)
     */
    @Override
    protected Response onInent(IntentRequest intentRequest) {
        /** 判断NLU解析的意图名称是否匹配 */
        if ("inquiry".equals(intentRequest.getIntentName())) {
            /** 判断NLU解析解析后是否存在这个槽位 */
            if (getSlot("monthlysalary") == null) {

                // 询问月薪槽位
                this.ask("monthlysalary");
                return this.askSalary();

            } else if (getSlot("location") == null) {

                // 询问城市槽位
                this.ask("location");
                return this.askLocation();

            } else if (getSlot("compute_type") == null) {

                // 询问查询种类槽位
                this.ask("compute_type");
                return this.askComputeType();

            } else {
                // 具体计算方法
                this.compute();
            }
        }
        return null;
    }

    /**
     * 重写onSessionEnded事件，处理onSessionEnded对话事件
     * 
     * @param sessionEndedRequest
     *            SessionEndedRequest请求体
     * @see com.baidu.dueros.bot.Base#onSessionEnded(com.baidu.dueros.data.request.SessionEndedRequest)
     */
    @Override
    protected Response onSessionEnded(SessionEndedRequest sessionEndedRequest) {
        TextCard textCard = new TextCard("感谢使用所得税服务");
        textCard.setAnchorText("setAnchorText");
        textCard.addCueWord("欢迎再次使用");

        OutputSpeech outputSpeech = new OutputSpeech(Type.SSML, "欢迎再次使用所得税服务");

        Response response = new Response(outputSpeech, textCard);
        return response;
    }

    /**
     * 询问城市信息
     * 
     * @return Response 返回Response
     */
    private Response askLocation() {
        TextCard textCard = new TextCard("您所在的城市是哪里呢?");
        textCard.setAnchorText("setAnchorText");
        textCard.addCueWord("您所在的城市是哪里呢?");

        this.setSessionAttribute("key_1", "value_1");
        this.setSessionAttribute("key_2", "value_2");

        OutputSpeech outputSpeech = new OutputSpeech(Type.SSML, "您所在的城市是哪里呢?");

        Reprompt reprompt = new Reprompt(outputSpeech);

        Response response = new Response(outputSpeech, textCard, reprompt);
        return response;
    }

    /**
     * 询问月薪
     * 
     * @return Response 返回Response
     */
    private Response askSalary() {
        TextCard textCard = new TextCard("您的税前工资是多少呢?");
        textCard.setUrl("www:......");
        textCard.setAnchorText("链接文本");
        textCard.addCueWord("您的税前工资是多少呢?");

        /** 设置会话信息 */
        this.setSessionAttribute("key_1", "value_1");
        this.setSessionAttribute("key_2", "value_2");

        OutputSpeech outputSpeech = new OutputSpeech(Type.SSML, "java-sdk您的税前工资是多少呢?");

        /** 构造reprompt */
        Reprompt reprompt = new Reprompt(outputSpeech);

        Response response = new Response(outputSpeech, textCard, reprompt);
        Resource resource = new Resource();
        response.setResource(resource);
        return response;
    }

    /**
     * 询问个税种类
     * 
     * @return Response 返回Response
     */
    private Response askComputeType() {
        TextCard textCard = new TextCard("请选择您要查询的种类");
        textCard.setAnchorText("setAnchorText");
        textCard.addCueWord("请选择您要查询的种类");

        this.setSessionAttribute("key_1", "value_1");
        this.setSessionAttribute("key_2", "value_2");

        OutputSpeech outputSpeech = new OutputSpeech(Type.SSML, "请选择您要查询的种类");

        Reprompt reprompt = new Reprompt(outputSpeech);

        Response response = new Response(outputSpeech, textCard, reprompt);
        return response;
    }

    private Response compute() {
        String ret = "需要缴纳个税245元，扣除五险一金后剩余6756元";

        TextCard textCard = new TextCard(ret);
        textCard.setAnchorText("setAnchorText");
        textCard.addCueWord("查询成功");

        this.setSessionAttribute("key_1", "value_1");
        this.setSessionAttribute("key_2", "value_2");

        OutputSpeech outputSpeech = new OutputSpeech(Type.SSML, ret);

        Reprompt reprompt = new Reprompt(outputSpeech);
        /** 主动结束会话 */
        this.endDialog();

        Response response = new Response(outputSpeech, textCard, reprompt);
        return response;
    }
}