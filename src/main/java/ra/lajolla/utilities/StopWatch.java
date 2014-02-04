/*
 * Copyright (c) Raphael A. Bauer (mechanical.bauer@gmail.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 */
package ra.lajolla.utilities;

public class StopWatch 
{
	private long m_startTime;
	private long m_stopTime;
	private boolean m_isActive;
	
	/*
	 * 
	 */
	public void startTime()
	{
		m_startTime = System.currentTimeMillis();
		m_isActive = true;
	}
	
	/*
	 * 
	 */	
	public void stopTime()
	{
		m_stopTime = System.currentTimeMillis();
		m_isActive = false;
	}
	
	/*
	 * 
	 */
	public int getTime()
	{
		if (m_isActive)
			stopTime();
		return (int) (m_stopTime - m_startTime);
	}	
}

