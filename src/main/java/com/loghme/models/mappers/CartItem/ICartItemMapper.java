package com.loghme.models.mappers.CartItem;

import com.loghme.database.Mapper.IMapper;
import com.loghme.models.domain.CartItem.CartItem;
import com.loghme.models.mappers.utils.TripleKey.TripleKey;

interface ICartItemMapper extends IMapper<CartItem, TripleKey> {}
