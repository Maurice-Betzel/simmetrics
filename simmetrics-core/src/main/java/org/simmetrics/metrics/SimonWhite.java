/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package org.simmetrics.metrics;

import java.util.List;

import org.simmetrics.ListMetric;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * Simon White algorithm providing a similarity measure between two lists. Idea
 * taken from <a href="http://www.catalysoft.com/articles/StrikeAMatch.html">How
 * to Strike a Match</a> by Simon White.
 * 
 * <p>
 * <code>
 * similarity(a,b) = 2 * |(a A b)|  / (|a| + |b|)
 * </code>
 * 
 * <p>
 * The A operation takes the list intersection of <code>a</code> and
 * <code>b</code>. This is a list <code>c</code> such that each element in has a
 * 1-to-1 relation to an element in both <code>a</code> and <code>b</code>. E.g.
 * the list intersection of <code>[ab,ab,ab,ac]</code> and
 * <code>[ab,ab,ad]</code> is <code>[ab,ab]</code>.
 * 
 * <p>
 * This metric is very similar to Dice's coefficient however Simon White used
 * the list intersection rather then the set intersection to prevent list of
 * duplicates from scoring a perfect match against a list with single elements.
 * E.g. 'GGGGG' should not be identical to 'GG'.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see DiceSimilarity
 * 
 * 
 * @param <T>
 *            type of the token
 * 
 */
public class SimonWhite<T> implements ListMetric<T> {

	@Override
	public float compare(List<T> a, List<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		// Copy for destructive list difference
		Multiset<T> bCopy = HashMultiset.create(b);

		// Count elements in the list intersection.
		// Elements are counted only once in both lists.
		// E.g. the intersection of [ab,ab,ab] and [ab,ab,ac,ad] is [ab,ab].
		// Note: this is not the same as b.retainAll(a).size()
		int intersection = 0;
		for (T token : a) {
			if (bCopy.remove(token)) {
				intersection++;
			}
		}

		return 2.0f * intersection / (a.size() + b.size());

	}

	@Override
	public String toString() {
		return "SimonWhite";
	}

}
