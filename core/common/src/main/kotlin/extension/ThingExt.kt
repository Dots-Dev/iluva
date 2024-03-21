package extension

import so.kciter.thing.normalizer.NormalizationRuleBuilder

fun NormalizationRuleBuilder<String?>.trim() = addNormalizer {
    it?.trim()
}