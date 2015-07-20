/*
 * Copyright 2013 Google Inc.
 * Copyright 2015 BitTechCenter Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bitcoinj.params;

import org.bitcoinj.core.NetworkParameters;
import org.coinj.api.CoinDefinition;

/**
 * Parameters for the old version 2 testnet. This is not useful to you - it exists only because some unit tests are
 * based on it.
 */
public class TestNet2Params extends NetworkParameters {

    private static final long serialVersionUID = 1;

    public static final String TEST_NET2_NET_ID = "oldTestNet2";
    public static final String OLD_TEST_ID = "org.bitcoin.oldtest2";

    public TestNet2Params(CoinDefinition coinDefinition) {
        super(coinDefinition);

        standardNetworkId = new CoinDefinition.StandardNetworkId() {
            private static final long serialVersionUID = 1L;

            @Override
            public String str() {
                return TEST_NET2_NET_ID;
            }

            @Override
            public boolean equals(Object o) {
                return this == o || !(o == null || !(o instanceof CoinDefinition.StandardNetworkId)) && TEST_NET2_NET_ID.equals(((CoinDefinition.StandardNetworkId) o).str());
            }

            @Override
            public int hashCode() {
                return TEST_NET2_NET_ID.hashCode();
            }

        };
        id = OLD_TEST_ID;

        fillProtectedValues();
    }

    @Override
    public String getPaymentProtocolId() {
        return null;
    }

}
