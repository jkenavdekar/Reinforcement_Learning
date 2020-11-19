public class StateAndReward {

	
	/* State discretization function for the angle controller */
	public static String getStateAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */

		String state = "OneStateToRuleThemAll";
		
		if (angle > -0.1 && angle < 0.1)
			state = "N";
		else if(angle >= 0.1 && angle < 0.3 )
			state = "NE";
		else if(angle >= 0.3 && angle < 0.5 )
			state = "E";
		else if(angle >= 0.5 && angle < 0.7 )
			state = "SE";
		else if(angle >= 0.7 && angle < Math.PI )
			state = "S";
		else if(angle <= -0.1 && angle > -0.3 )
			state = "NW";
		else if(angle <= -0.3 && angle > -0.5 )
			state = "W";
		else if(angle <= -0.5 && angle > -0.7 )
			state = "SW";
		else if(angle <= -0.7 && angle > -Math.PI )
			state = "S";
		
		return state;
	}

	/* Reward function for the angle controller */
	public static double getRewardAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		
		double reward = 0;

		String state = StateAndReward.getStateAngle(angle, vx, vy);
		if(state == "N")
			reward = 1.0;
		else if (state == "NE" || state == "NW")
			reward = 0.8;
		else if (state == "E" || state == "W")
			reward = 0.25;
		else if (state == "SE" || state == "SW")
			reward = -0.25;
		else if (state == "S")
			reward = -1.0;
		
		return reward;
	}

	/* State discretization function for the full hover controller */
	public static String getStateHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */

		String state = "OneStateToRuleThemAll2";
		
		if (angle > -0.1 && angle < 0.1)
			state = "N";
		else if(angle >= 0.1 && angle < 0.3 )
			state = "Y";//NE
		else if(angle >= 0.3 && angle < 0.5 )
			state = "E";
		else if(angle >= 0.5 && angle < 0.7 )
			state = "Z";//SE
		else if((angle >= 0.7 && angle < Math.PI) || (angle <= -0.7 && angle > -Math.PI))
			state = "S";
		else if(angle < -0.1 && angle >= -0.3 )
			state = "P";//NW
		else if(angle < -0.3 && angle >= -0.5 )
			state = "W";
		else if(angle < -0.5 && angle >= -0.7 )
			state = "Q";//SW
		
		if(vx >= -0.2 && vx <= 0.3)
			state = state + "A";
		else if(vx > 0.3 && vx <= 0.6)
			state = state + "B";
		else if(vx < -0.2  && vx >= -0.6)
			state = state + "C";
		else
			state = state + "D";
		
		if(vy >= -1 && vy <= 1)
			state = state + "a";
		else if(vy < -1 && vy >= -2.5)
			state = state + "b";
		else if(vy > 1  && vy <= 2)
			state = state + "c";
		else
			state = state + "d";
		
		return state;
	}

	/* Reward function for the full hover controller */
	public static double getRewardHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		
		double reward = 0;
		String state = StateAndReward.getStateHover(angle, vx, vy);
		
		/*
		if( state.contains("N") || state.contains("Y") || state.contains("P") && state.contains("a") && !state.contains("D") && !state.contains("d"))
			reward = 1.0;
		else if ( state.contains("E") || state.contains("W") && state.contains("b") && !state.contains("D") && !state.contains("d"))
			reward = 0.8;
		else if (state.contains("Z") || state.contains("Q") && !state.contains("D") && !state.contains("d"))
			reward = 0.5;
		else if (state.contains("S") && !state.contains("D") && !state.contains("d"))
			reward = 0.25;
		else
			reward = -1.0;
		
		*/
		if(state.contains("N")) 
		{
			reward = reward + 1.0;
			if(state.contains("a") || state.contains("b") || state.contains("c")) 
			{
				reward = reward + 1.0;
				if(state.contains("A")) 
				{
					reward = reward + 1.0;
				}
			}
		}
		else if(state.contains("Y") || state.contains("P")) 
		{
			reward = reward + 1.0;
			if(state.contains("a") || state.contains("b") || state.contains("c")) 
			{
				reward = reward + 1.0;
				if(state.contains("A") || state.contains("B") || state.contains("C")) 
				{
					reward = reward + 1.0;
				}
			}
		}
		
		

		return reward;
	}

	// ///////////////////////////////////////////////////////////
	// discretize() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 1 and nrValues-2 is returned.
	//
	// Use discretize2() if you want a discretization method that does
	// not handle values lower than min and higher than max.
	// ///////////////////////////////////////////////////////////
	public static int discretize(double value, int nrValues, double min,
			double max) {
		if (nrValues < 2) {
			return 0;
		}

		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * (nrValues - 2)) + 1;
	}

	// ///////////////////////////////////////////////////////////
	// discretize2() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 0 and nrValues-1 is returned.
	// ///////////////////////////////////////////////////////////
	public static int discretize2(double value, int nrValues, double min,
			double max) {
		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * nrValues);
	}

}
