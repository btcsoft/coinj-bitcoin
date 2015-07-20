package org.coinj.bitcoin;

import org.bitcoinj.tools.BuildCheckpoints;
import org.coinj.api.CoinLocator;

/**
 * Date: 6/24/15
 * Time: 2:59 PM
 *
 * @author Mikhail Kulikov
 */
public final class BuildBitcoinCheckpoints {

    public static void main(String[] args) {
        CoinLocator.registerCoin(BitcoinDefinition.INSTANCE);
        try {
            BuildCheckpoints.main(args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private BuildBitcoinCheckpoints() {}

}
