/*
 * gVirtuS -- A GPGPU transparent virtualization component.
 *
 * Copyright (C) 2009-2010  The University of Napoli Parthenope at Naples.
 *
 * This file is part of gVirtuS.
 *
 * gVirtuS is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * gVirtuS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with gVirtuS; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Written by: Flora Giannone <flora.giannone@studenti.uniparthenope.it>,
 *             Department of Applied Science
 */


#ifndef _CUDADRBACKEND_H
#define	_CUDADRBACKEND_H
#include "Observer.h"
#include "Communicator.h"
#include "Backend.h"
#include <cuda.h>


/**
 * Backend is the main object of gvirtus-backend. It is responsible of accepting
 * the connection from the Frontend(s) and spawing a new Process for handling
 * each Frontend.
 */
class CudaDrBackend : public Backend {
public:
    Handler *GetHandler();

};
#endif	/* _CUDADRBACKEND_H */

