package org.mintflow.simple.handle;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.HandlerType;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.simple.bean.Buy;
import org.mintflow.simple.bean.Goods;
import org.mintflow.simple.bean.ResMsg;
import org.mintflow.util.CollectionUtils;

import java.util.List;

/**
 * 商品价格计算器
 */
@MintFlowHandler(name="price_calculation_async_handle")
public class PriceCalculationAsyncHandle extends AsyncSampleFnHandler {


    public PriceCalculationAsyncHandle(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper params, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        Buy buy = params.getParam(Buy.class);
        List<Goods> goodsList = buy.getGoodsList();
        ResMsg resMsg = new ResMsg();
        double money = 0;
        if(!CollectionUtils.isNullOrEmpty(goodsList)) {
            for (Goods goods : goodsList) {
                money+= getGoodsMoney(goods.getItem());
            }
        }
        resMsg.setMoney( money);
        params.setParam(resMsg.getClass(),resMsg);
        asyncScheduler.next(params,asyncResult);
    }

    private double getGoodsMoney(String goodsName){
        switch (goodsName){
            case "A":
                return 10;
            case "B":
                return 20;
            case "C":
                return 30;
            default:
                return 0;
        }
    }
}
