package org.simmetrics.simplifiers;

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.commons.codec.language.MatchRatingApproachEncoder;

import com.google.common.base.Preconditions;

/**
 * Match Rating Approach Phonetic Algorithm Developed by <CITE>Western Airlines</CITE> in 1977.
 *
 * This class is immutable and thread-safe.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Match_rating_approach">Wikipedia - Match Rating Approach</a>
 * @see org.apache.commons.codec.language.MatchRatingApproachEncoder
 */
public class MatchRatingApproach implements Simplifier {

	private final MatchRatingApproachEncoder simplifier = new MatchRatingApproachEncoder();

	@Override
	public String simplify(String input) {
		checkNotNull(input);
		return simplifier.encode(input);
	}
	
	@Override
	public String toString() {
		return "MatchRatingApproach";
	}

}
